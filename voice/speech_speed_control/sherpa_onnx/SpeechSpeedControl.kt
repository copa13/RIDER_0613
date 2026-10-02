package com.rider.speech_speed_control.sherpa_onnx

import com.k2fsa.sherpa.onnx.OfflineTts

class SpeechSpeedControl(
    private val tts: OfflineTts
) {

    private var speed = 1.0f

    fun setSpeed(value: Float) {
        require(value > 0f) {
            "Speed must be greater than 0"
        }

        speed = value
    }

    fun getSpeed(): Float {
        return speed
    }

    fun generate(
        text: String,
        voiceId: Int = 0
    ): FloatArray {

        require(text.isNotBlank()) {
            "Text cannot be empty"
        }

        val audio = tts.generate(
            text = text,
            sid = voiceId,
            speed = speed
        )

        return audio.samples
    }

    fun release() {
        tts.release()
    }
}
