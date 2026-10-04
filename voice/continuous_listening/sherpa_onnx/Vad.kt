package com.rider.continuouslistening.sherpa_onnx

import android.content.res.AssetManager
import com.k2fsa.sherpa.onnx.SileroVadModelConfig as NativeSileroVadModelConfig
import com.k2fsa.sherpa.onnx.Vad as SherpaVad
import com.k2fsa.sherpa.onnx.VadModelConfig as NativeVadModelConfig

/** Parameters for sherpa-onnx's real Silero VAD model. */
data class SileroVadConfig(
    val model: String = "silero_vad_v5.onnx",
    val threshold: Float = 0.5f,
    val minSilenceDuration: Float = 0.25f,
    val minSpeechDuration: Float = 0.25f,
    val windowSize: Int = 512,
    val maxSpeechDuration: Float = 5.0f
)

data class VadConfig(
    val sampleRate: Int = 16000,
    val numThreads: Int = 1,
    val provider: String = "cpu",
    val silero: SileroVadConfig = SileroVadConfig()
)

/** Feeds complete Silero windows and reports model-backed speech state. */
class RiderVad(
    assetManager: AssetManager,
    private val config: VadConfig = VadConfig()
) : AutoCloseable {
    private val window = FloatArray(config.silero.windowSize)
    private val nativeVad: SherpaVad
    private var pendingCount = 0
    private var speechDetected = false
    private var released = false

    val windowSize: Int get() = config.silero.windowSize

    init {
        require(config.sampleRate == 16000) { "Silero VAD requires 16 kHz audio" }
        require(config.silero.windowSize == 512) { "Silero VAD v5 requires 512-sample windows at 16 kHz" }
        require(config.numThreads > 0) { "numThreads must be positive" }
        require(config.silero.threshold in 0.0f..1.0f) { "threshold must be in [0, 1]" }

        val nativeConfig = NativeVadModelConfig(
            sileroVadModelConfig = NativeSileroVadModelConfig(
                model = config.silero.model,
                threshold = config.silero.threshold,
                minSilenceDuration = config.silero.minSilenceDuration,
                minSpeechDuration = config.silero.minSpeechDuration,
                windowSize = config.silero.windowSize,
                maxSpeechDuration = config.silero.maxSpeechDuration
            ),
            sampleRate = config.sampleRate,
            numThreads = config.numThreads,
            provider = config.provider,
            debug = false
        )
        nativeVad = SherpaVad(assetManager, nativeConfig)
    }

    /** Accepts arbitrary chunk lengths and internally feeds exact model windows. */
    @Synchronized
    fun acceptWaveform(samples: FloatArray): Boolean {
        check(!released) { "VAD has been released" }
        var sourceOffset = 0
        while (sourceOffset < samples.size) {
            val copied = minOf(window.size - pendingCount, samples.size - sourceOffset)
            System.arraycopy(samples, sourceOffset, window, pendingCount, copied)
            sourceOffset += copied
            pendingCount += copied

            if (pendingCount == window.size) {
                nativeVad.acceptWaveform(window)
                speechDetected = nativeVad.isSpeechDetected()
                pendingCount = 0
            }
        }
        return speechDetected
    }

    @Synchronized
    fun isSpeechDetected(): Boolean {
        check(!released) { "VAD has been released" }
        return speechDetected
    }

    @Synchronized
    fun reset() {
        check(!released) { "VAD has been released" }
        nativeVad.reset()
        pendingCount = 0
        speechDetected = false
    }

    @Synchronized
    override fun close() {
        if (released) return
        released = true
        nativeVad.release()
        pendingCount = 0
        speechDetected = false
    }

    fun release() = close()
}
