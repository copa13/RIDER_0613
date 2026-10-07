package com.rider.voice.speech_state_machine.droidkaigi_gvp

class SpeechPipelineStateMachine(
    initialState: SpeechPipelineState = SpeechPipelineState.IDLE
) {

    private var currentState = initialState

    private val transitions = setOf(
        SpeechPipelineTransition(
            SpeechPipelineState.IDLE,
            SpeechPipelineEvent.START_LISTENING,
            SpeechPipelineState.LISTENING
        ),

        SpeechPipelineTransition(
            SpeechPipelineState.LISTENING,
            SpeechPipelineEvent.SPEECH_STARTED,
            SpeechPipelineState.SPEECH_DETECTED
        ),

        SpeechPipelineTransition(
            SpeechPipelineState.SPEECH_DETECTED,
            SpeechPipelineEvent.SPEECH_ENDED,
            SpeechPipelineState.PROCESSING
        ),

        SpeechPipelineTransition(
            SpeechPipelineState.PROCESSING,
            SpeechPipelineEvent.PROCESSING_COMPLETE,
            SpeechPipelineState.EXECUTING
        ),

        SpeechPipelineTransition(
            SpeechPipelineState.EXECUTING,
            SpeechPipelineEvent.EXECUTION_COMPLETE,
            SpeechPipelineState.SPEAKING
        ),

        SpeechPipelineTransition(
            SpeechPipelineState.SPEAKING,
            SpeechPipelineEvent.SPEAKING_COMPLETE,
            SpeechPipelineState.LISTENING
        ),

        SpeechPipelineTransition(
            SpeechPipelineState.SPEAKING,
            SpeechPipelineEvent.USER_INTERRUPTED,
            SpeechPipelineState.INTERRUPTED
        ),

        SpeechPipelineTransition(
            SpeechPipelineState.INTERRUPTED,
            SpeechPipelineEvent.START_LISTENING,
            SpeechPipelineState.LISTENING
        ),

        SpeechPipelineTransition(
            SpeechPipelineState.LISTENING,
            SpeechPipelineEvent.PAUSE,
            SpeechPipelineState.PAUSED
        ),

        SpeechPipelineStateMachineTransition(
            SpeechPipelineState.SPEECH_DETECTED,
            SpeechPipelineEvent.PAUSE,
            SpeechPipelineState.PAUSED
        ),

        SpeechPipelineStateMachineTransition(
            SpeechPipelineState.PROCESSING,
            SpeechPipelineEvent.PAUSE,
            SpeechPipelineState.PAUSED
        ),

        SpeechPipelineStateMachineTransition(
            SpeechPipelineState.EXECUTING,
            SpeechPipelineEvent.PAUSE,
            SpeechPipelineState.PAUSED
        ),

        SpeechPipelineStateMachineTransition(
            SpeechPipelineState.SPEAKING,
            SpeechPipelineEvent.PAUSE,
            SpeechPipelineState.PAUSED
        ),

        SpeechPipelineTransition(
            SpeechPipelineState.PAUSED,
            SpeechPipelineEvent.RESUME,
            SpeechPipelineState.LISTENING
        )
    )

    fun getState(): SpeechPipelineState {
        return currentState
    }

    fun transition(
        event: SpeechPipelineEvent
    ): Boolean {

        val transition = transitions.firstOrNull {
            it.from == currentState &&
                it.event == event
        }

        if (transition == null) {
            return false
        }

        currentState = transition.to
        return true
    }

    fun reset() {
        currentState = SpeechPipelineState.IDLE
    }

    fun forceError(): Boolean {
        currentState = SpeechPipelineState.ERROR
        return true
    }

    fun recoverFromError(): Boolean {
        if (currentState != SpeechPipelineState.ERROR) {
            return false
        }

        currentState = SpeechPipelineState.IDLE
        return true
    }
}

private data class SpeechPipelineStateMachineTransition(
    val from: SpeechPipelineState,
    val event: SpeechPipelineEvent,
    val to: SpeechPipelineState
)
