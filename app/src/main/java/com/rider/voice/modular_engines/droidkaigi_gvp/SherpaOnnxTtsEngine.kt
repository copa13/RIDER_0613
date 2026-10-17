package com.rider.voice.modular_engines.droidkaigi_gvp

import com.k2fsa.sherpa.onnx.OfflineTts
import com.k2fsa.sherpa.onnx.OfflineTtsConfig

class SherpaOnnxTtsEngine(
    private val config: OfflineTtsConfig,
    private val speakerId: Int = 0,
    private val speed: Float = 1.0f,
    override val engineId: String = "sherpa-onnx-tts"
) : SpeechSynthesisEngine {

    override val isOffline: Boolean = true

    private var tts: OfflineTts? = null

    init {
        require(speakerId >= 0)
        require(speed > 0f)
    }

    @Synchronized
    override fun initialize(): Boolean {
        if (tts != null) return true

        return try {
            tts = OfflineTts(config = config)
            true
        } catch (_: Exception) {
            tts = null
            false
        }
    }

    @Synchronized
    override fun synthesize(
        text: String
    ): SpeechEngineResult<ByteArray> {
        val active = tts
            ?: return SpeechEngineResult.Failure(
                engineId,
                "TTS engine has not been initialized."
            )

        if (text.isBlank()) {
            return SpeechEngineResult.Failure(
                engineId,
                "Text is empty."
            )
        }

        return try {
            val generated = active.generate(
                text = text,
                sid = speakerId,
                speed = speed
            )

            if (generated.samples.isEmpty()) {
                SpeechEngineResult.Failure(
                    engineId,
                    "TTS generated no audio."
                )
            } else {
                SpeechEngineResult.Success(
                    PcmWavEncoder.encode(
                        samples = generated.samples,
                        sampleRate = generated.sampleRate
                    )
                )
            }
        } catch (error: Exception) {
            SpeechEngineResult.Failure(
                engineId,
                error.message ?: "sherpa-onnx TTS failed."
            )
        }
    }

    @Synchronized
    override fun release() {
        tts?.release()
        tts = null
    }
}
