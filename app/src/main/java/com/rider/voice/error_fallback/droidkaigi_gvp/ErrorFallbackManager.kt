package com.rider.voice.error_fallback.droidkaigi_gvp

class ErrorFallbackManager(
    private val fallbackChain: FallbackChain,
    private val retryPolicy: RetryPolicy = RetryPolicy()
) {

    private var lastRequestId: Long? = null

    fun handle(error: RiderError): FallbackResult {

        if (isStaleRequest(error.requestId)) {
            return FallbackResult.Unhandled(
                response = "The previous request is no longer active."
            )
        }

        if (error.requestId != null) {
            lastRequestId = error.requestId
        }

        if (retryPolicy.canRetry(error)) {
            return FallbackResult.Retry(
                reason = error.message
            )
        }

        retryPolicy.reset()

        val result = fallbackChain.handle(error)

        return when (result) {

            is FallbackResult.Recovered ->
                result

            is FallbackResult.Handled ->
                result

            is FallbackResult.Retry ->
                result

            is FallbackResult.Failed ->
                result

            is FallbackResult.Unhandled ->
                FallbackResult.Unhandled(
                    response = ErrorMessageProvider.messageFor(error)
                )
        }
    }

    fun reset() {
        lastRequestId = null
        retryPolicy.reset()
    }

    private fun isStaleRequest(
        requestId: Long?
    ): Boolean {

        if (requestId == null) {
            return false
        }

        val previous = lastRequestId ?: return false

        return requestId < previous
    }
}
