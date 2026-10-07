package com.rider.voice.speech_state_machine.droidkaigi_gvp

sealed class SpeechPipelineStateResult {

    data class Accepted(
        val snapshot: SpeechPipelineSnapshot
    ) : SpeechPipelineStateResult()

    data class Rejected(
        val snapshot: SpeechPipelineSnapshot
    ) : SpeechPipelineStateResult()

    data class Error(
        val snapshot: SpeechPipelineSnapshot,
        val message: String
    ) : SpeechPipelineStateResult()
}
