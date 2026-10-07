package com.rider.voice.latency_measurement.droidkaigi_gvp

class LatencyTracker {

    private var turnCounter = 0L

    private var speechStartMs: Long? = null
    private var speechEndMs: Long? = null
    private var sttCompleteMs: Long? = null
    private var commandProcessedMs: Long? = null
    private var actionCompleteMs: Long? = null
    private var ttsStartMs: Long? = null

    private val completedEndToEnd = mutableListOf<Long>()

    fun startTurn(
        nowMs: Long = System.nanoTime() / 1_000_000
    ): Long {
        turnCounter++

        speechStartMs = nowMs
        speechEndMs = null
        sttCompleteMs = null
        commandProcessedMs = null
        actionCompleteMs = null
        ttsStartMs = null

        return turnCounter
    }

    fun markSpeechEnd(
        nowMs: Long = now()
    ) {
        speechEndMs = nowMs
    }

    fun markSttComplete(
        nowMs: Long = now()
    ) {
        sttCompleteMs = nowMs
    }

    fun markCommandProcessed(
        nowMs: Long = now()
    ) {
        commandProcessedMs = nowMs
    }

    fun markActionComplete(
        nowMs: Long = now()
    ) {
        actionCompleteMs = nowMs
    }

    fun markTtsStart(
        nowMs: Long = now()
    ) {
        ttsStartMs = nowMs
    }

    fun buildTrace(): LatencyTrace {

        val endToEnd =
            if (speechStartMs != null && ttsStartMs != null) {
                ttsStartMs!! - speechStartMs!!
            } else {
                null
            }

        if (endToEnd != null) {
            completedEndToEnd.add(endToEnd)
        }

        return LatencyTrace(
            turnId = turnCounter,
            speechStartMs = speechStartMs,
            speechEndMs = speechEndMs,
            sttCompleteMs = sttCompleteMs,
            commandProcessedMs = commandProcessedMs,
            actionCompleteMs = actionCompleteMs,
            ttsStartMs = ttsStartMs,
            endToEndMs = endToEnd
        )
    }

    fun getEndToEndMetrics(): LatencyMetrics {
        return calculateMetrics(completedEndToEnd)
    }

    fun resetMetrics() {
        completedEndToEnd.clear()
    }

    private fun calculateMetrics(
        values: List<Long>
    ): LatencyMetrics {

        if (values.isEmpty()) {
            return LatencyMetrics(
                count = 0,
                minMs = null,
                maxMs = null,
                meanMs = null,
                medianMs = null,
                p95Ms = null,
                p99Ms = null
            )
        }

        val sorted = values.sorted()

        return LatencyMetrics(
            count = sorted.size,
            minMs = sorted.first(),
            maxMs = sorted.last(),
            meanMs = sorted.average(),
            medianMs = percentile(sorted, 50.0),
            p95Ms = percentile(sorted, 95.0),
            p99Ms = percentile(sorted, 99.0)
        )
    }

    private fun percentile(
        sorted: List<Long>,
        percentile: Double
    ): Long {

        if (sorted.size == 1) {
            return sorted[0]
        }

        val position =
            (percentile / 100.0) * (sorted.size - 1)

        val lower = position.toInt()
        val upper = kotlin.math.ceil(position).toInt()

        if (lower == upper) {
            return sorted[lower]
        }

        val weight = position - lower

        return (
            sorted[lower] +
                (sorted[upper] - sorted[lower]) * weight
            ).toLong()
    }

    private fun now(): Long {
        return System.nanoTime() / 1_000_000
    }
}
