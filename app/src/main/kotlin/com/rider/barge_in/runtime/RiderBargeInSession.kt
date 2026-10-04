package com.rider.barge_in.runtime

import android.content.res.AssetManager
import com.rider.continuouslistening.sherpa_onnx.RiderVad
import com.rider.tts_pause_stop.sherpa_onnx.TtsPauseStop
import com.rider.user_speech_stops_tts.sherpa_onnx.BargeInController
import com.rider.user_speech_stops_tts.sherpa_onnx.CancellableAiResponseState
import java.io.File

/** Feature #31 composition point for the existing RIDER TTS and future AI host. */
class RiderBargeInSession(
    assetManager: AssetManager,
    private val echoReference: BargeInEchoReference,
    private val responseState: CancellableAiResponseState,
    private val ttsPlayback: TtsPauseStop,
    onCaptureFailure: (Throwable) -> Unit = {}
) : AutoCloseable {
    private val vad = RiderVad(assetManager)
    private val bargeInController = BargeInController(
        stopTts = ttsPlayback::stop,
        cancelResponse = { responseState.cancelActive() }
    )
    private val microphone = BargeInMicrophoneMonitor(
        vad = vad,
        echoReference = echoReference,
        onSpeechStarted = {
            if (ttsPlayback.isPlaying()) {
                bargeInController.onTtsStarted()
                bargeInController.onUserSpeechDetected()
            } else {
                bargeInController.onTtsStopped()
            }
        },
        onFailure = onCaptureFailure
    )

    @Volatile
    private var started = false

    @Volatile
    private var closed = false

    @Synchronized
    fun start() {
        check(!closed) { "Barge-in session is closed" }
        if (started) return
        echoReference.start(BargeInMicrophoneMonitor.SAMPLE_RATE)
        try {
            microphone.start()
            started = true
        } catch (failure: Exception) {
            echoReference.stop()
            throw failure
        }
    }

    /** Uses the existing Feature #20 MediaPlayer path and arms Feature #21. */
    fun playTtsFile(audioFile: File) {
        check(started && !closed) { "Start the barge-in session before TTS playback" }
        ttsPlayback.play(audioFile)
        bargeInController.onTtsStarted()
    }

    /** For playback started by the host rather than playTtsFile(). */
    fun onTtsStarted() {
        check(started && !closed) { "Start the barge-in session before TTS playback" }
        check(ttsPlayback.isPlaying()) { "The existing TTS player is not playing" }
        bargeInController.onTtsStarted()
    }

    /** Pass actual 16 kHz mono PCM before it is sent to the speaker. */
    fun onTtsRenderAudio(samples: ShortArray) {
        check(started && !closed) { "Start the barge-in session before TTS playback" }
        check(ttsPlayback.isPlaying() && bargeInController.isTtsPlaying()) {
            "TTS render data arrived while playback is stopped"
        }
        echoReference.processRenderAudio(samples)
    }

    fun onTtsStopped() {
        ttsPlayback.stop()
        bargeInController.onTtsStopped()
    }

    @Synchronized
    override fun close() {
        if (closed) return
        closed = true
        bargeInController.onTtsStopped()
        responseState.cancelActive()
        try {
            ttsPlayback.stop()
        } finally {
            microphone.stop()
            vad.close()
            echoReference.stop()
            started = false
        }
    }
}
