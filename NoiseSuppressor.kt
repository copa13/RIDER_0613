package com.rider.noise_suppression.webrtc_apm

import org.webrtc.AudioFrame
import org.webrtc.AudioProcessing
import org.webrtc.AudioProcessingBuilder

class NoiseSuppressor {

    private var audioProcessing: AudioProcessing? = null

    fun start() {
        if (audioProcessing != null) return

        audioProcessing = AudioProcessingBuilder()
            .setNoiseSuppression(true)
            .setNoiseSuppressionLevel(
                AudioProcessing.NoiseSuppressionLevel.High
            )
            .build()
    }

    fun processFrame(frame: AudioFrame) {
        checkNotNull(audioProcessing) {
            "Noise suppressor is not started"
        }

        audioProcessing?.processStream(frame)
    }

    fun stop() {
        audioProcessing?.release()
        audioProcessing = null
    }

    fun isRunning(): Boolean {
        return audioProcessing != null
    }
}
