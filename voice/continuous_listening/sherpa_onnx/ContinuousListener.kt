package com.rider.continuouslistening

import android.Manifest
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import androidx.core.app.ActivityCompat
import com.k2fsa.sherpa.onnx.OnlineRecognizer

class ContinuousListener(
    private val recognizer: OnlineRecognizer
) {
    private var audioRecord: AudioRecord? = null
    private var recording = false

    private val sampleRate = 16000
    private val bufferSize = (0.1 * sampleRate).toInt()

    fun start() {
        if (recording) return

        val minBuffer = AudioRecord.getMinBufferSize(
            sampleRate,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT
        )

        audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            sampleRate,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            maxOf(minBuffer, bufferSize * 2)
        )

        audioRecord?.startRecording()
        recording = true

        Thread {
            val stream = recognizer.createStream()
            val buffer = ShortArray(bufferSize)

            while (recording) {
                val count = audioRecord?.read(
                    buffer,
                    0,
                    buffer.size
                ) ?: 0

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

                if (recognizer.isEndpoint(stream)) {
                    val text = recognizer.getResult(stream).text

                    if (text.isNotBlank()) {
                        println("RIDER: $text")
                    }

                    recognizer.reset(stream)
                }
            }
        }.start()
    }

    fun stop() {
        recording = false
        audioRecord?.stop()
        audioRecord?.release()
        audioRecord = null
    }
}

