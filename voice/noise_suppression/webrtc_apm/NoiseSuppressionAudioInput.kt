package com.rider.noise_suppression.webrtc_apm

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import org.webrtc.AudioFrame

class NoiseSuppressionAudioInput(
    private val noiseSuppressor: NoiseSuppressor,
    private val onProcessedAudio: (AudioFrame) -> Unit
) {

    private val sampleRate = 16000
    private val channelConfig = AudioFormat.CHANNEL_IN_MONO
    private val audioFormat = AudioFormat.ENCODING_PCM_16BIT

    @Volatile
    private var running = false

    fun start() {
        if (running) return

        val bufferSize = AudioRecord.getMinBufferSize(
            sampleRate,
            channelConfig,
            audioFormat
        )

        require(bufferSize > 0) {
            "Unable to initialize microphone"
        }

        val recorder = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            sampleRate,
            channelConfig,
            audioFormat,
            bufferSize * 2
        )

        noiseSuppressor.start()
        running = true

        Thread {
            val buffer = ShortArray(bufferSize)

            try {
                recorder.startRecording()

                while (running) {
                    val count = recorder.read(
                        buffer,
                        0,
                        buffer.size
                    )

                    if (count <= 0) continue

                    /*
                     * AudioFrame construction depends on the
                     * exact WebRTC AAR API.
                     *
                     * Once the AAR is available, this section
                     * creates the AudioFrame and sends it to:
                     *
                     * noiseSuppressor.processFrame(frame)
                     */
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
