package com.rider.barge_in.runtime

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import com.rider.continuouslistening.sherpa_onnx.RiderVad

/** Captures 16 kHz mono PCM, removes RIDER render echo, then feeds real VAD. */
class BargeInMicrophoneMonitor(
    private val vad: RiderVad,
    private val echoReference: BargeInEchoReference,
    private val onSpeechStarted: () -> Unit,
    private val onFailure: (Throwable) -> Unit = {}
) : AutoCloseable {
    private val lifecycleLock = Any()

    @Volatile
    private var running = false

    private var recorder: AudioRecord? = null
    private var worker: Thread? = null

    fun start() {
        synchronized(lifecycleLock) {
            if (running) return
            check(worker == null) { "The previous microphone capture is still shutting down" }

            val minBufferBytes = AudioRecord.getMinBufferSize(
                SAMPLE_RATE,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT
            )
            check(minBufferBytes > 0) { "Android could not configure the microphone" }

            val candidate = AudioRecord(
                MediaRecorder.AudioSource.MIC,
                SAMPLE_RATE,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                maxOf(minBufferBytes, VAD_WINDOW_SAMPLES * 4)
            )
            if (candidate.state != AudioRecord.STATE_INITIALIZED) {
                candidate.release()
                error("Android AudioRecord failed to initialize")
            }

            recorder = candidate
            running = true
            worker = Thread({ capture(candidate) }, "rider-barge-in-mic").also { it.start() }
        }
    }

    private fun capture(source: AudioRecord) {
        val readBuffer = ShortArray(VAD_WINDOW_SAMPLES)
        val pending = ShortArray(VAD_WINDOW_SAMPLES)
        var pendingCount = 0
        var wasSpeech = false

        try {
            source.startRecording()
            while (running) {
                val count = source.read(
                    readBuffer,
                    0,
                    readBuffer.size,
                    AudioRecord.READ_BLOCKING
                )
                if (count == 0) continue
                if (count < 0) error("AudioRecord read failed with code " + count)

                val cleaned = echoReference.processMicrophoneAudio(readBuffer.copyOf(count))
                var sourceOffset = 0
                while (sourceOffset < cleaned.size) {
                    val copied = minOf(VAD_WINDOW_SAMPLES - pendingCount, cleaned.size - sourceOffset)
                    System.arraycopy(cleaned, sourceOffset, pending, pendingCount, copied)
                    sourceOffset += copied
                    pendingCount += copied

                    if (pendingCount == VAD_WINDOW_SAMPLES) {
                        val frame = FloatArray(VAD_WINDOW_SAMPLES) {
                            pending[it].toFloat() / 32768.0f
                        }
                        val isSpeech = vad.acceptWaveform(frame)
                        if (isSpeech && !wasSpeech) onSpeechStarted()
                        wasSpeech = isSpeech
                        pendingCount = 0
                    }
                }
            }
        } catch (failure: Exception) {
            if (running) onFailure(failure)
        } finally {
            try {
                if (source.recordingState == AudioRecord.RECORDSTATE_RECORDING) source.stop()
            } catch (_: IllegalStateException) {
                // stop() may already have interrupted the blocking read.
            }
            source.release()
            synchronized(lifecycleLock) {
                if (recorder === source) {
                    recorder = null
                    worker = null
                    running = false
                }
            }
        }
    }

    fun stop() {
        val source: AudioRecord?
        val captureThread: Thread?
        synchronized(lifecycleLock) {
            running = false
            source = recorder
            captureThread = worker
        }

        try {
            if (source?.recordingState == AudioRecord.RECORDSTATE_RECORDING) source.stop()
        } catch (_: IllegalStateException) {
            // The capture loop owns final cleanup if recording did not start.
        }

        if (captureThread != null && captureThread !== Thread.currentThread()) {
            try {
                captureThread.join(1000)
            } catch (_: InterruptedException) {
                Thread.currentThread().interrupt()
            }
        }
    }

    override fun close() = stop()

    companion object {
        const val SAMPLE_RATE = 16000
        const val VAD_WINDOW_SAMPLES = 512
    }
}
