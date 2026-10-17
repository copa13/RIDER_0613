package com.rider.voice.modular_engines.droidkaigi_gvp

interface SpeechSynthesisEngine {
    val engineId: String
    val isOffline: Boolean

    fun initialize(): Boolean

    fun synthesize(
        text: String
    ): SpeechEngineResult<ByteArray>

    fun release()
}
