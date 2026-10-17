package com.rider.voice.modular_engines.droidkaigi_gvp

data class SpeechEngineDescriptor(
    val engineId: String,
    val type: SpeechEngineType,
    val offline: Boolean,
    val available: Boolean = true,
    val priority: Int = 100
)
