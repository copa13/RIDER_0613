package com.rider.voice.latency_measurement.droidkaigi_gvp

data class LatencyTrace(
    val turnId: Long,
    val speechStartMs: Long?,
    val speechEndMs: Long?,
    val sttCompleteMs: Long?,
    val commandProcessedMs: Long?,
    val actionCompleteMs: Long?,
    val ttsStartMs: Long?,
    val endToEndMs: Long?
)
