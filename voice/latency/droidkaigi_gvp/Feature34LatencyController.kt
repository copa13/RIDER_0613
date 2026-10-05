package com.rider.latency.droidkaigi_gvp

class Feature34LatencyController(
    private val onLatencyMeasured: (Long) -> Unit = {}
) {

    private var speechEndTime = 0L
    private var processingStartTime = 0L
    private var responseReadyTime = 0L
    private var ttsStartTime = 0L

    private var active = false

    /**
     * Call when the user's speech has ended.
     *
     * This is the starting point for response latency measurement.
     */
    fun onSpeechEnded() {
        speechEndTime = now()
        processingStartTime = speechEndTime
        responseReadyTime = 0L
        ttsStartTime = 0L
        active = true
    }

    /**
     * Call immediately when response/AI processing is started.
     */
    fun onProcessingStarted() {
        if (!active) return

        processingStartTime = now()
    }

    /**
     * Call as soon as the response becomes ready.
     */
    fun onResponseReady() {
        if (!active) return

        responseReadyTime = now()
    }

    /**
     * Call at the exact moment TTS starts producing speech.
     *
     * This is the important end point for Feature #34.
     */
    fun onTtsStarted() {
        if (!active) return

        ttsStartTime = now()

        val latency = ttsStartTime - speechEndTime

        if (latency >= 0L) {
            onLatencyMeasured(latency)
        }
    }

    /**
     * Complete the current latency cycle.
     */
    fun finish() {
        active = false
        speechEndTime = 0L
        processingStartTime = 0L
        responseReadyTime = 0L
        ttsStartTime = 0L
    }

    /**
     * Reset without recording a result.
     */
    fun reset() {
        finish()
    }

    fun isActive(): Boolean {
        return active
    }

    /**
     * Total response latency:
     *
     * User speech ended
     *        ↓
     * TTS started
     */
    fun totalLatencyMs(): Long? {
        if (speechEndTime == 0L || ttsStartTime == 0L) {
            return null
        }

        return ttsStartTime - speechEndTime
    }

    /**
     * Time spent in AI/response processing.
     */
    fun processingLatencyMs(): Long? {
        if (
            processingStartTime == 0L ||
            responseReadyTime == 0L
        ) {
            return null
        }

        return responseReadyTime - processingStartTime
    }

    /**
     * Time from response becoming ready
     * until TTS actually starts.
     */
    fun ttsStartupLatencyMs(): Long? {
        if (
            responseReadyTime == 0L ||
            ttsStartTime == 0L
        ) {
            return null
        }

        return ttsStartTime - responseReadyTime
    }

    private fun now(): Long {
        return System.nanoTime() / 1_000_000L
    }
}
