package com.rider.voice_commands.droidkaigi_gvp

class VoiceCommandEngine(
    private val parser: VoiceCommandParser,
    private val executor: VoiceCommandExecutor
) {

    fun process(text: String): CommandExecutionResult {
        val command = parser.parse(text)

        if (command == null) {
            return CommandExecutionResult.Failure(
                input = text,
                reason = "Command not recognized"
            )
        }

        return if (executor.execute(command)) {
            CommandExecutionResult.Success(command)
        } else {
            CommandExecutionResult.Failure(
                input = text,
                reason = "Command execution failed"
            )
        }
    }
}
