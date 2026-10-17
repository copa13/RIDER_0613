package com.rider.voice.modular_engines.droidkaigi_gvp

data class PcmAudioInput(
    val samples: FloatArray,
    val sampleRate: Int
) {
    init {
        require(sampleRate > 0) {
            "Sample rate must be positive."
        }
        require(samples.isNotEmpty()) {
            "Audio samples must not be empty."
        }
    }
}
