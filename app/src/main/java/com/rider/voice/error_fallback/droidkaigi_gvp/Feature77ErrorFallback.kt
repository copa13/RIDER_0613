package com.rider.voice.error_fallback.droidkaigi_gvp

class Feature77ErrorFallback(
    handlers: List<FallbackHandler>
) {

    private val manager = ErrorFallbackManager(
        fallbackChain = FallbackChain(handlers)
    )

    fun handle(error: RiderError): FallbackResult {
        return manager.handle(error)
    }

    fun reset() {
        manager.reset()
    }
}
