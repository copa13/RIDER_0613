package com.rider.continuouslistening.sherpa_onnx

data class SileroVadConfig(
    val model: String = "silero_vad.onnx",
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

class RiderVad(
    private val config: VadConfig
) {
    fun acceptWaveform(samples: FloatArray) {
        // Connect to sherpa-onnx VAD implementation during integration.
    }

    fun isSpeechDetected(): Boolean {
        return false
    }

    fun reset() {
        // Reset VAD state.
    }
}
