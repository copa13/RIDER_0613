package com.rider.voice.speech_state_machine.droidkaigi_gvp

enum class SpeechPipelineEvent {
    START_LISTENING,
    SPEECH_STARTED,
    SPEECH_ENDED,
    START_PROCESSING,
    PROCESSING_COMPLETE,
    START_EXECUTION,
    EXECUTION_COMPLETE,
    START_SPEAKING,
    SPEAKING_COMPLETE,
    USER_INTERRUPTED,
    PAUSE,
    RESUME,
    ERROR,
    RESET
}
