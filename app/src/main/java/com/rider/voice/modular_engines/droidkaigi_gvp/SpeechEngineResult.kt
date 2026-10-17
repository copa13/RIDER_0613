package com.rider.voice.modular_engines.droidkaigi_gvp

sealed class SpeechEngineResult<out T> {
    data class Success<T>(
        val value: T
    ) : SpeechEngineResult<T>()

    data class Failure(
        val engineId: String,
        val message: String,
        val recoverable: Boolean = true
    ) : SpeechEngineResult<Nothing>()
}
