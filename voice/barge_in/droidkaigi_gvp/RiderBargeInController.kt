// voice/barge_in/droidkaigi_gvp/RiderBargeInController.kt

package com.rider.barge_in.droidkaigi_gvp

class RiderBargeInController(
    private val cancelResponse: () -> Unit,
    private val stopTts: () -> Unit
) {

    private var ttsActive = false
    private var ttsStartedAt = 0L
    private var lastTriggerAt = 0L

    fun onTtsStarted() {
        ttsActive = true
        ttsStartedAt = System.currentTimeMillis()
    }

    fun onTtsStopped() {
        ttsActive = false
        ttsStartedAt = 0L
    }

    fun onSpeechDetected(
        speechEnergy: Float,
        threshold: Float = 0.025f
    ) {
        if (!ttsActive) return

        val now = System.currentTimeMillis()

        // Ignore residual speaker echo immediately after TTS starts.
        if (now - ttsStartedAt < 1200L) return

        // Prevent repeated triggers from the same speech event.
        if (now - lastTriggerAt < 150L) return

        if (speechEnergy < threshold) return

        lastTriggerAt = now

        // Cancel the current AI response.
        cancelResponse()

        // Stop the current TTS playback.
        stopTts()

        ttsActive = false
        ttsStartedAt = 0L
    }

    fun isTtsActive(): Boolean {
        return ttsActive
    }
}
