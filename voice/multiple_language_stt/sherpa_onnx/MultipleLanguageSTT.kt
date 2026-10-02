package com.rider.multiple_language_stt.sherpa_onnx

import com.k2fsa.sherpa.onnx.OfflineRecognizer

class MultipleLanguageSTT(
    private val recognizer: OfflineRecognizer
) {

    fun recognize(
        samples: FloatArray,
        sampleRate: Int,
        language: String
    ): String {

        val stream = recognizer.createStream()

        stream.setOption(
            "language",
            language
        )

        stream.acceptWaveform(
            samples,
            sampleRate
        )

        recognizer.decode(stream)

        val result = recognizer
            .getResult(stream)
            .text
            .trim()

        stream.release()

        return result
    }
}
