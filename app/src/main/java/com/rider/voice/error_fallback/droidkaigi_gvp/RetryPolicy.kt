package com.rider.voice.error_fallback.droidkaigi_gvp

class RetryPolicy(
    private val maxRetries: Int = 1
) {

    private var retryCount = 0

    fun canRetry(error: RiderError): Boolean {
        if (!error.retryable) {
            return false
        }

        if (retryCount >= maxRetries) {
            return false
        }

        retryCount++
        return true
    }

    fun reset() {
        retryCount = 0
    }

    fun getRetryCount(): Int {
        return retryCount
    }
}
