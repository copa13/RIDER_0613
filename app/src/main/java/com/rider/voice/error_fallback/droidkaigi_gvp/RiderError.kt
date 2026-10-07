package com.rider.voice.error_fallback.droidkaigi_gvp

data class RiderError(
    val type: ErrorType,
    val message: String,
    val recoverable: Boolean = true,
    val retryable: Boolean = false,
    val requestId: Long? = null
)
