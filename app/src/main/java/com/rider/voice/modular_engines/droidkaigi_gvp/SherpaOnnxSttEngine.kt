package com.rider.voice.modular_engines.droidkaigi_gvp

import com.k2fsa.sherpa.onnx.OfflineRecognizer
import com.k2fsa.sherpa.onnx.OfflineRecognizerConfig
import com.k2fsa.sherpa.onnx.OfflineStream

class SherpaOnnxSttEngine(
    private val config: OfflineRecognizerConfig,
    override val engineId: String = "sherpa-onnx-stt"
) : SpeechRecognitionEngine {

    override val isOffline: Boolean = true

    private var recognizer: OfflineRecognizer? = null

    @Synchronized
    override fun initialize(): Boolean {
        if (recognizer != null) return true

        return try {
            recognizer = OfflineRecognizer(config = config)
            true
        } catch (_: Exception) {
            recognizer = null
            false
        }
    }

    @Synchronized
    override fun recognize(
        audio: PcmAudioInput
    ): SpeechEngineResult<String> {
        val active = recognizer
            ?: return SpeechEngineResult.Failure(
                engineId,
                "STT engine has not been initialized."
            )

        var stream: OfflineStream? = null

        return try {
            stream = active.createStream()

            stream.acceptWaveform(
                samples = audio.samples,
                sampleRate = audio.sampleRate
            )

            active.decode(stream)

            val text = active.getResult(stream).text.trim()

            if (text.isEmpty()) {
                SpeechEngineResult.Failure(
                    engineId,
                    "No speech was recognized."
                )
            } else {
                SpeechEngineResult.Success(text)
            }
        } catch (error: Exception) {
            SpeechEngineResult.Failure(
                engineId,
                error.message ?: "sherpa-onnx STT failed."
            )
        } finally {
            stream?.release()
        }
    }

    @Synchronized
    override fun release() {
        recognizer?.release()
        recognizer = null
    }
}
