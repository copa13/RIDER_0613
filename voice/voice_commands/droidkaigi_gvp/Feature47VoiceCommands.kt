package com.rider.voice_commands.droidkaigi_gvp

class Feature47VoiceCommands(
    private val parser: VoiceCommandParser,
    private val executor: VoiceCommandExecutor,
    private val onCommandExecuted: (VoiceCommand) -> Unit = {},
    private val onCommandRejected: (String) -> Unit = {}
) {

    fun handleSpeech(text: String): Boolean {
        val command = parser.parse(text)

        if (command == null) {
            onCommandRejected(text)
            return false
        }

        val executed = executor.execute(command)

        if (executed) {
            onCommandExecuted(command)
        } else {
            onCommandRejected(text)
        }

        return executed
    }
}
