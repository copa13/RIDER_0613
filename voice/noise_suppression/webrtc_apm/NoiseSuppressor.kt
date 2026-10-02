package com.rider.noise_suppression.webrtc_apm

import org.webrtc.AudioProcessing
import org.webrtc.AudioProcessingBuilder
import org.webrtc.NoiseSuppression

class NoiseSuppressor {

    private var audioProcessing: AudioProcessing? = null

    fun start() {
        if (audioProcessing != null) return

        val builder = AudioProcessingBuilder()

        val noiseSuppression = builder.createNoiseSuppression()
        noiseSuppression.enable(true)
        noiseSuppression.level = NoiseSuppression.Level.HIGH

        audioProcessing = builder.createAudioProcessing()
    }

    fun process(frame: org.webrtc.AudioFrame) {
        check(audioProcessing != null) {
            "Noise suppressor is not started"
        }

        audioProcessing?.processFrame(frame)
    }

    fun stop() {
        audioProcessing?.release()
        audioProcessing = null
    }
}
