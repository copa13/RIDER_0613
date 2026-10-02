package com.rider.offline_tts.sherpa_onnx

import com.k2fsa.sherpa.onnx.OfflineTts

class OfflineTTS(
    private val tts: OfflineTts
) {

    fun generate(
        text: String,
        speakerId: Int = 0,
        speed: Float = 1.0f
    ): FloatArray {

        require(text.isNotBlank()) {
            "Text cannot be empty"
        }

        require(speed > 0f) {
            "Speed must be greater than 0"
        }

        val audio = tts.generate(
            text = text,
            sid = speakerId,
            speed = speed
        )

        return audio.samples
    }

    fun sampleRate(): Int {
        return tts.sampleRate()
    }

    fun release() {
        tts.release()
    }
}
