package com.rider.multiple_voices.sherpa_onnx

import com.k2fsa.sherpa.onnx.OfflineTts

class MultipleVoices(
    private val tts: OfflineTts
) {

    fun generate(
        text: String,
        voiceId: Int,
        speed: Float = 1.0f
    ): FloatArray {

        require(text.isNotBlank()) {
            "Text cannot be empty"
        }

        require(voiceId >= 0) {
            "Invalid voice ID"
        }

        val audio = tts.generate(
            text = text,
            sid = voiceId,
            speed = speed
        )

        return audio.samples
    }

    fun voice(
        text: String,
        voiceId: Int
    ): FloatArray {
        return generate(
            text = text,
            voiceId = voiceId
        )
    }

    fun release() {
        tts.release()
    }
}
