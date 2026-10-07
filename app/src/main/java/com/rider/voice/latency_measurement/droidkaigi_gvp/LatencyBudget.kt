package com.rider.voice.latency_measurement.droidkaigi_gvp

data class LatencyBudget(
    val vadMs: Long? = null,
    val sttMs: Long? = null,
    val commandProcessingMs: Long? = null,
    val actionMs: Long? = null,
    val ttsStartMs: Long? = null,
    val endToEndMs: Long? = null
)
