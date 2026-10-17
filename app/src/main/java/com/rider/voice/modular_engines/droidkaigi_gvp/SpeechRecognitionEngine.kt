package com.rider.voice.modular_engines.droidkaigi_gvp

interface SpeechRecognitionEngine {
    val engineId: String
    val isOffline: Boolean

    fun initialize(): Boolean

    fun recognize(
        audio: PcmAudioInput
    ): SpeechEngineResult<String>

    fun release()
}
