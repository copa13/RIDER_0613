// voice/barge_in/droidkaigi_gvp/BargeInController.kt

package com.rider.barge_in.droidkaigi_gvp

class BargeInController(
    private val stopTts: () -> Unit,
    private val cancelResponse: () -> Unit
) {
    private var ttsStartedAt = 0L
    private var lastTriggerAt = 0L

    fun onTtsStarted() {
        ttsStartedAt = System.currentTimeMillis()
    }

    fun onTtsStopped() {
        ttsStartedAt = 0L
    }

    fun onSpeechDetected(
        speechEnergy: Float,
        threshold: Float = 0.025f
    ) {
        val now = System.currentTimeMillis()

        // TTS-onset grace period: ignore initial residual echo.
        if (now - ttsStartedAt < 1200L) return

        // Debounce repeated VAD events.
        if (now - lastTriggerAt < 150L) return

        if (speechEnergy < threshold) return

        lastTriggerAt = now

        cancelResponse()
        stopTts()
        ttsStartedAt = 0L
    }

    fun isTtsPlaying(): Boolean {
        return ttsStartedAt != 0L
    }
}
