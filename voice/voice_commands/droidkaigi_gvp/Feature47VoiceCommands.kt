package com.rider.voice_commands.droidkaigi_gvp

class Feature47VoiceCommands(
    private val parser: VoiceCommandParser,
    private val executor: VoiceCommandExecutor,
    private val onCommandExecuted: (VoiceCommand) -> Unit = {},
    private val onCommandFailed: (String) -> Unit = {}
) {

    fun handleSpeech(
        speechText: String
    ): Boolean {

        val command = parser.parse(speechText)

        if (command == null) {
            onCommandFailed(speechText)
            return false
        }

        val executed = executor.execute(command)

        if (executed) {
            onCommandExecuted(command)
        } else {
            onCommandFailed(speechText)
        }

        return executed
    }
}
