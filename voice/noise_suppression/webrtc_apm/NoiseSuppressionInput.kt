package com.rider.noise_suppression.webrtc_apm

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder

class NoiseSuppressionInput(
    private val noiseSuppressor: NoiseSuppressor,
    private val onCleanAudio: (ShortArray, Int) -> Unit
) {
    private val sampleRate = 16000
    private val channel = AudioFormat.CHANNEL_IN_MONO
    private val format = AudioFormat.ENCODING_PCM_16BIT

    @Volatile
    private var running = false

    fun start() {
        if (running) return

        val minBuffer = AudioRecord.getMinBufferSize(
            sampleRate,
            channel,
            format
        )

        require(minBuffer > 0) {
            "Unable to initialize microphone"
        }

        val recorder = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            sampleRate,
            channel,
            format,
            minBuffer * 2
        )

        noiseSuppressor.start()
        running = true

        Thread {
            val buffer = ShortArray(minBuffer)

            try {
                recorder.startRecording()

                while (running) {
                    val count = recorder.read(
                        buffer,
                        0,
                        buffer.size
                    )

                    if (count <= 0) continue

                    val frame = buffer.copyOf(count)

                    /*
                     * WebRTC AudioFrame processing is handled
                     * by NoiseSuppressor.
                     *
                     * The processed frame is then forwarded
                     * to the RIDER speech pipeline.
                     */
                    val cleanAudio =
                        noiseSuppressor.process(frame, sampleRate)

                    onCleanAudio(
                        cleanAudio,
                        sampleRate
                    )
                }
            } finally {
                recorder.stop()
                recorder.release()
                noiseSuppressor.stop()
                running = false
            }
        }.start()
    }

    fun stop() {
        running = false
    }
}
