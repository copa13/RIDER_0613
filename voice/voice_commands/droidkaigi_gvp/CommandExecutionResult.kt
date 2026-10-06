package com.rider.voice_commands.droidkaigi_gvp

sealed class CommandExecutionResult {

    data class Success(
        val command: VoiceCommand
    ) : CommandExecutionResult()

    data class Failure(
        val input: String,
        val reason: String
    ) : CommandExecutionResult()
}
