package com.rider.voice.error_fallback.droidkaigi_gvp

sealed class FallbackResult {

    data object Recovered : FallbackResult()

    data class Handled(
        val response: String
    ) : FallbackResult()

    data class Retry(
        val reason: String
    ) : FallbackResult()

    data class Failed(
        val response: String
    ) : FallbackResult()

    data class Unhandled(
        val response: String
    ) : FallbackResult()
}
