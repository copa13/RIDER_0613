package com.rider.user_speech_stops_tts.sherpa_onnx

/** Feature #21 controller, reused by Feature #31. */
class BargeInController(
    private val stopTts: () -> Unit,
    private val cancelResponse: () -> Unit = {}
) {
    private val lock = Any()
    private var ttsPlaying = false

    fun onTtsStarted() {
        synchronized(lock) { ttsPlaying = true }
    }

    fun onTtsStopped() {
        synchronized(lock) { ttsPlaying = false }
    }

    /** Stop audible output immediately, then cancel the response exactly once. */
    fun onUserSpeechDetected() {
        val shouldInterrupt = synchronized(lock) {
            if (!ttsPlaying) {
                false
            } else {
                ttsPlaying = false
                true
            }
        }
        if (!shouldInterrupt) return

        try {
            stopTts()
        } finally {
            cancelResponse()
        }
    }

    fun isTtsPlaying(): Boolean = synchronized(lock) { ttsPlaying }
}
