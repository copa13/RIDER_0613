package com.rider.voice.speech_state_machine.droidkaigi_gvp

data class SpeechPipelineSnapshot(
    val state: SpeechPipelineState,
    val lastEvent: SpeechPipelineEvent?,
    val transitionAccepted: Boolean,
    val sequence: Long
)
