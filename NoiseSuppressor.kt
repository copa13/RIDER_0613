package com.rider.noise_suppression.webrtc_apm

import org.webrtc.AudioProcessing
import org.webrtc.AudioProcessingBuilder

class NoiseSuppressor {

    private var processor: AudioProcessing? = null

    fun start() {
        if (processor != null) return

        processor = AudioProcessingBuilder()
            .setNoiseSuppression(true)
            .setNoiseSuppressionLevel(
                AudioProcessing.NoiseSuppressionLevel.High
            )
            .build()
    }

    fun stop() {
        processor?.dispose()
        processor = null
    }

    fun isRunning(): Boolean {
        return processor != null
    }
}
