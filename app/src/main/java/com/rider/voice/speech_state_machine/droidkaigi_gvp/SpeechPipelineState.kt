package com.rider.voice.speech_state_machine.droidkaigi_gvp

enum class SpeechPipelineState {
    IDLE,
    LISTENING,
    SPEECH_DETECTED,
    PROCESSING,
    EXECUTING,
    SPEAKING,
    INTERRUPTED,
    ERROR,
    PAUSED
}
