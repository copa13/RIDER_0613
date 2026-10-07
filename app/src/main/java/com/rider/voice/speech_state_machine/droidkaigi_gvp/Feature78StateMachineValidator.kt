package com.rider.voice.speech_state_machine.droidkaigi_gvp

object Feature78StateMachineValidator {

    fun isListeningState(
        state: SpeechPipelineState
    ): Boolean {
        return state == SpeechPipelineState.LISTENING ||
            state == SpeechPipelineState.SPEECH_DETECTED
    }

    fun isProcessingState(
        state: SpeechPipelineState
    ): Boolean {
        return state == SpeechPipelineState.PROCESSING ||
            state == SpeechPipelineState.EXECUTING
    }

    fun isSpeakingState(
        state: SpeechPipelineState
    ): Boolean {
        return state == SpeechPipelineState.SPEAKING
    }

    fun isInterruptedState(
        state: SpeechPipelineState
    ): Boolean {
        return state == SpeechPipelineState.INTERRUPTED
    }

    fun isTerminalErrorState(
        state: SpeechPipelineState
    ): Boolean {
        return state == SpeechPipelineState.ERROR
    }

    fun canAcceptUserSpeech(
        state: SpeechPipelineState
    ): Boolean {
        return state == SpeechPipelineState.LISTENING ||
            state == SpeechPipelineState.SPEECH_DETECTED ||
            state == SpeechPipelineState.SPEAKING
    }
}
