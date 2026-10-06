package com.rider.tool_calling.droidkaigi_gvp

sealed class ToolCallResult {

    data class Success(
        val command: VoiceCommand
    ) : ToolCallResult()

    data class Failed(
        val command: VoiceCommand,
        val message: String
    ) : ToolCallResult()

    data class Invalid(
        val message: String
    ) : ToolCallResult()
}
