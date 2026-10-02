package com.rider.continuous_listening.sherpa_onnx

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import com.k2fsa.sherpa.onnx.OnlineRecognizer

class RiderContinuousListening(
    private val recognizer: OnlineRecognizer,
    private val onSpeech: (String) -> Unit
) {
    private val sampleRate = 16000
    private val channel = AudioFormat.CHANNEL_IN_MONO
    private val format = AudioFormat.ENCODING_PCM_16BIT

    @Volatile
    private var running = false

    fun start() {
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
            val stream = recognizer.createStream()
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

                while (recognizer.isReady(stream)) {
                    recognizer.decode(stream)
                }

                val text = recognizer
                    .getResult(stream)
                    .text
                    .trim()

                if (text.isNotEmpty()) {
                    onSpeech(text)
                    recognizer.reset(stream)
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
