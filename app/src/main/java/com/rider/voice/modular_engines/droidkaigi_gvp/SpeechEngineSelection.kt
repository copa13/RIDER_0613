package com.rider.voice.modular_engines.droidkaigi_gvp

data class SpeechEngineSelection(
    val sttEngineId: String,
    val ttsEngineId: String,
    val offlineOnly: Boolean = true
)
