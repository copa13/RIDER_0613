package com.rider.natural_language.droidkaigi_gvp

import com.rider.voice_commands.droidkaigi_gvp.VoiceCommand
import com.rider.voice_commands.droidkaigi_gvp.VoiceCommandExecutor

class NaturalLanguageCommandEngine(
    private val parser: NaturalLanguageIntentParser,
    private val validator: NaturalLanguageValidator,
    private val converter: NaturalLanguageToVoiceCommand,
    private val executor: VoiceCommandExecutor,
    private val onRejected: (String) -> Unit = {}
) {

    fun process(
        userText: String
    ): Boolean {

        val result =
            parser.parse(userText)
                ?: run {
                    onRejected(userText)
                    return false
                }

        if (!validator.validate(result)) {
            onRejected(userText)
            return false
        }

        val command =
            converter.convert(result.intent)
                ?: run {
                    onRejected(userText)
                    return false
                }

        return execute(command, userText)
    }

    private fun execute(
        command: VoiceCommand,
        originalText: String
    ): Boolean {

        val success = executor.execute(command)

        if (!success) {
            onRejected(originalText)
        }

        return success
    }
}
