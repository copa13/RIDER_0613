package com.rider.voice.error_fallback.droidkaigi_gvp

interface FallbackHandler {

    val priority: Int

    fun canHandle(error: RiderError): Boolean

    fun handle(error: RiderError): FallbackResult
}
