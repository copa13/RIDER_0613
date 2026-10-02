package com.rider.offline_stt.sherpa_onnx

import com.k2fsa.sherpa.onnx.OfflineRecognizer

class OfflineSTT(
    private val recognizer: OfflineRecognizer
) {

    fun recognize(
        samples: FloatArray,
        sampleRate: Int
    ): String {

        val stream = recognizer.createStream()

        stream.acceptWaveform(
            samples,
            sampleRate
        )

        recognizer.decode(stream)

        val text = recognizer
            .getResult(stream)
            .text
            .trim()

        stream.release()

        return text
    }
}
