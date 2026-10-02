package com.rider.continuouslistening.sherpa_onnx

import com.k2fsa.sherpa.onnx.OnlineRecognizer
import com.k2fsa.sherpa.onnx.OnlineStream

class EndOfSpeechDetector(
    private val recognizer: OnlineRecognizer
) {

    fun check(
        stream: OnlineStream
    ): String? {

        if (!recognizer.isEndpoint(stream)) {
            return null
        }

        val text = recognizer.getResult(stream).text

        recognizer.reset(stream)

        return text.ifBlank {
            null
        }
    }
}
