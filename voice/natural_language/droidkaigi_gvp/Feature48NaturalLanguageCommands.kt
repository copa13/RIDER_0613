package com.rider.natural_language.droidkaigi_gvp

import com.rider.voice_commands.droidkaigi_gvp.VoiceCommandExecutor

class Feature48NaturalLanguageCommands(
    executor: VoiceCommandExecutor,
    onRejected: (String) -> Unit = {}
) {

    private val engine = NaturalLanguageCommandEngine(
        parser = NaturalLanguageIntentParser(),
        validator = NaturalLanguageValidator(),
        converter = NaturalLanguageToVoiceCommand(),
        executor = executor,
        onRejected = onRejected
    )

    fun handleSpeech(
        text: String
    ): Boolean {
        return engine.process(text)
    }
}
