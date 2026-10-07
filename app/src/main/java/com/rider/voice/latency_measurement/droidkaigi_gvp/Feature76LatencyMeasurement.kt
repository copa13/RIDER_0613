package com.rider.voice.latency_measurement.droidkaigi_gvp

class Feature76LatencyMeasurement {

    private val tracker = LatencyTracker()

    fun startTurn(): Long {
        return tracker.startTurn()
    }

    fun markSpeechEnd() {
        tracker.markSpeechEnd()
    }

    fun markSttComplete() {
        tracker.markSttComplete()
    }

    fun markCommandProcessed() {
        tracker.markCommandProcessed()
    }

    fun markActionComplete() {
        tracker.markActionComplete()
    }

    fun markTtsStart() {
        tracker.markTtsStart()
    }

    fun finishTurn(): LatencyTrace {
        return tracker.buildTrace()
    }

    fun getMetrics(): LatencyMetrics {
        return tracker.getEndToEndMetrics()
    }

    fun resetMetrics() {
        tracker.resetMetrics()
    }
}
