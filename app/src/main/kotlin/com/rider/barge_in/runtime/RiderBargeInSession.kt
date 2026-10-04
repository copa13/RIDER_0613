package com.rider.barge_in.runtime

import android.content.res.AssetManager
import com.rider.continuouslistening.sherpa_onnx.RiderVad
import com.rider.user_speech_stops_tts.sherpa_onnx.BargeInController
import com.rider.user_speech_stops_tts.sherpa_onnx.CancellableAiResponseState

/** Feature #31 composition point for the real RIDER microphone/TTS/AI host. */
class RiderBargeInSession(
    assetManager: AssetManager,
    private val echoReference: BargeInEchoReference,
    private val responseState: CancellableAiResponseState,
    private val stopTtsImmediately: () -> Unit,
    onCaptureFailure: (Throwable) -> Unit = {}
) : AutoCloseable {
    private val vad = RiderVad(assetManager)
    private val bargeInController = BargeInController(
        stopTts = stopTtsImmediately,
        cancelResponse = { responseState.cancelActive() }
    )
    private val microphone = BargeInMicrophoneMonitor(
        vad = vad,
        echoReference = echoReference,
        onSpeechStarted = { bargeInController.onUserSpeechDetected() },
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

    fun onTtsStarted() {
        check(started && !closed) { "Start the barge-in session before TTS playback" }
        bargeInController.onTtsStarted()
    }

    /** Pass actual 16 kHz mono PCM before it is sent to the speaker. */
    fun onTtsRenderAudio(samples: ShortArray) {
        check(started && !closed) { "Start the barge-in session before TTS playback" }
        check(bargeInController.isTtsPlaying()) { "TTS render data arrived while playback is stopped" }
        echoReference.processRenderAudio(samples)
    }

    fun onTtsStopped() = bargeInController.onTtsStopped()

    @Synchronized
    override fun close() {
        if (closed) return
        closed = true
        val wasPlaying = bargeInController.isTtsPlaying()
        bargeInController.onTtsStopped()
        responseState.cancelActive()
        try {
            if (wasPlaying) stopTtsImmediately()
        } finally {
            microphone.stop()
            vad.close()
            echoReference.stop()
            started = false
        }
    }
}
