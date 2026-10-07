package com.rider.voice.speech_state_machine.droidkaigi_gvp

class Feature78SpeechPipelineStateMachine(
    initialState: SpeechPipelineState = SpeechPipelineState.IDLE
) {

    private val controller =
        SpeechPipelineController(initialState)

    fun startListening(): SpeechPipelineStateResult =
        controller.dispatch(
            SpeechPipelineEvent.START_LISTENING
        )

    fun speechStarted(): SpeechPipelineStateResult =
        controller.dispatch(
            SpeechPipelineEvent.SPEECH_STARTED
        )

    fun speechEnded(): SpeechPipelineStateResult =
        controller.dispatch(
            SpeechPipelineEvent.SPEECH_ENDED
        )

    fun startProcessing(): SpeechPipelineStateResult =
        controller.dispatch(
            SpeechPipelineEvent.START_PROCESSING
        )

    fun processingComplete(): SpeechPipelineStateResult =
        controller.dispatch(
            SpeechPipelineEvent.PROCESSING_COMPLETE
        )

    fun startExecution(): SpeechPipelineStateResult =
        controller.dispatch(
            SpeechPipelineEvent.START_EXECUTION
        )

    fun executionComplete(): SpeechPipelineStateResult =
        controller.dispatch(
            SpeechPipelineEvent.EXECUTION_COMPLETE
        )

    fun startSpeaking(): SpeechPipelineStateResult =
        controller.dispatch(
            SpeechPipelineEvent.START_SPEAKING
        )

    fun speakingComplete(): SpeechPipelineStateResult =
        controller.dispatch(
            SpeechPipelineEvent.SPEAKING_COMPLETE
        )

    fun userInterrupted(): SpeechPipelineStateResult =
        controller.dispatch(
            SpeechPipelineEvent.USER_INTERRUPTED
        )

    fun pause(): SpeechPipelineStateResult =
        controller.dispatch(
            SpeechPipelineEvent.PAUSE
        )

    fun resume(): SpeechPipelineStateResult =
        controller.dispatch(
            SpeechPipelineEvent.RESUME
        )

    fun error(): SpeechPipelineStateResult =
        controller.forceError()

    fun recoverFromError(): Boolean =
        controller.recoverFromError()

    fun getState(): SpeechPipelineState =
        controller.getState()

    fun snapshot(): SpeechPipelineSnapshot =
        controller.snapshot()

    fun reset() {
        controller.reset()
    }
}
