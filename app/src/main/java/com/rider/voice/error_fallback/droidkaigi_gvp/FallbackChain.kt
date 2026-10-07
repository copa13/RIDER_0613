package com.rider.voice.error_fallback.droidkaigi_gvp

class FallbackChain(
    handlers: List<FallbackHandler>
) {

    private val orderedHandlers =
        handlers.sortedBy { it.priority }

    fun handle(error: RiderError): FallbackResult {

        for (handler in orderedHandlers) {

            if (!handler.canHandle(error)) {
                continue
            }

            return try {
                when (val result = handler.handle(error)) {
                    is FallbackResult.Recovered ->
                        result

                    is FallbackResult.Handled ->
                        result

                    is FallbackResult.Retry ->
                        result

                    is FallbackResult.Failed ->
                        continue

                    is FallbackResult.Unhandled ->
                        continue
                }
            } catch (_: Exception) {
                continue
            }
        }

        return FallbackResult.Unhandled(
            response = "I could not complete that request."
        )
    }
}
