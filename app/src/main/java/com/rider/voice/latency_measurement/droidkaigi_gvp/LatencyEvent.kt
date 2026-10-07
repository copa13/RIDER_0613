package com.rider.voice.latency_measurement.droidkaigi_gvp

data class LatencyEvent(
    val stage: LatencyStage,
    val timestampMs: Long
)
