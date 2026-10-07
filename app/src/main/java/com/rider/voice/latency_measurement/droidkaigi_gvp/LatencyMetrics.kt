package com.rider.voice.latency_measurement.droidkaigi_gvp

data class LatencyMetrics(
    val count: Int,
    val minMs: Long?,
    val maxMs: Long?,
    val meanMs: Double?,
    val medianMs: Long?,
    val p95Ms: Long?,
    val p99Ms: Long?
)
