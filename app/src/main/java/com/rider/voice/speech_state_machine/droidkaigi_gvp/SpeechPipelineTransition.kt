package com.rider.voice.speech_state_machine.droidkaigi_gvp

data class SpeechPipelineTransition(
    val from: SpeechPipelineState,
    val event: SpeechPipelineEvent,
    val to: SpeechPipelineState
)
