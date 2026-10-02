package com.rider.user_speech_stops_tts.sherpa_onnx

class BargeInController(
    private val stopTts: () -> Unit
) {

    private var ttsPlaying = false

    fun onTtsStarted() {
        ttsPlaying = true
    }

    fun onTtsStopped() {
        ttsPlaying = false
    }

    fun onUserSpeechDetected() {
        if (!ttsPlaying) return

        stopTts()
        ttsPlaying = false
    }

    fun isTtsPlaying(): Boolean {
        return ttsPlaying
    }
}
