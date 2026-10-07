package com.rider.voice.speech_state_machine.droidkaigi_gvp

class SpeechPipelineController(
    initialState: SpeechPipelineState = SpeechPipelineState.IDLE
) {

    private val stateMachine =
        SpeechPipelineStateMachine(initialState)

    private var sequence = 0L
    private var lastEvent: SpeechPipelineEvent? = null

    fun dispatch(
        event: SpeechPipelineEvent
    ): SpeechPipelineStateResult {

        sequence++
        lastEvent = event

        return try {

            val accepted =
                stateMachine.transition(event)

            val snapshot = SpeechPipelineSnapshot(
                state = stateMachine.getState(),
                lastEvent = event,
                transitionAccepted = accepted,
                sequence = sequence
            )

            if (accepted) {
                SpeechPipelineStateResult.Accepted(snapshot)
            } else {
                SpeechPipelineStateResult.Rejected(snapshot)
            }

        } catch (error: Exception) {

            SpeechPipelineStateResult.Error(
                snapshot = SpeechPipelineSnapshot(
                    state = stateMachine.getState(),
                    lastEvent = event,
                    transitionAccepted = false,
                    sequence = sequence
                ),
                message = error.message
                    ?: "Speech pipeline state transition failed."
            )
        }
    }

    fun getState(): SpeechPipelineState {
        return stateMachine.getState()
    }

    fun snapshot(): SpeechPipelineSnapshot {
        return SpeechPipelineSnapshot(
            state = stateMachine.getState(),
            lastEvent = lastEvent,
            transitionAccepted = true,
            sequence = sequence
        )
    }

    fun reset() {
        stateMachine.reset()
        lastEvent = SpeechPipelineEvent.RESET
        sequence++
    }

    fun forceError(): SpeechPipelineStateResult {
        sequence++

        val accepted = stateMachine.forceError()

        val snapshot = SpeechPipelineSnapshot(
            state = stateMachine.getState(),
            lastEvent = SpeechPipelineEvent.ERROR,
            transitionAccepted = accepted,
            sequence = sequence
        )

        return SpeechPipelineStateResult.Accepted(snapshot)
    }

    fun recoverFromError(): Boolean {
        val recovered = stateMachine.recoverFromError()

        if (recovered) {
            lastEvent = SpeechPipelineEvent.RESET
            sequence++
        }

        return recovered
    }
}
