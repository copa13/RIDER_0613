package com.rider.wake_word.sherpa_onnx

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import com.k2fsa.sherpa.onnx.KeywordSpotter

class RiderWakeWord(
    private val kws: KeywordSpotter
) {
    private val sampleRate = 16000
    private val channel = AudioFormat.CHANNEL_IN_MONO
    private val format = AudioFormat.ENCODING_PCM_16BIT

    @Volatile
    private var running = false

    fun start(onWakeWord: () -> Unit) {
        if (running) return

        running = true

        val minBuffer = AudioRecord.getMinBufferSize(
            sampleRate,
            channel,
            format
        )

        val recorder = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            sampleRate,
            channel,
            format,
            minBuffer * 2
        )

        Thread {
            val stream = kws.createStream()
            val buffer = ShortArray(minBuffer)

            recorder.startRecording()

            while (running) {
                val count = recorder.read(
                    buffer,
                    0,
                    buffer.size
                )

                if (count <= 0) continue

                val samples = FloatArray(count) {
                    buffer[it] / 32768.0f
                }

                stream.acceptWaveform(
                    samples,
                    sampleRate
                )

                while (kws.isReady(stream)) {
                    kws.decode(stream)

                    val keyword = kws
                        .getResult(stream)
                        .keyword
                        .trim()

                    if (keyword.equals(
                            "hello rider",
                            ignoreCase = true
                        )
                    ) {
                        kws.reset(stream)
                        onWakeWord()
                    }
                }
            }

            stream.release()
            recorder.stop()
            recorder.release()
        }.start()
    }

    fun stop() {
        running = false
    }
}
