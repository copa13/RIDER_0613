package com.rider.voice.modular_engines.droidkaigi_gvp

class ModularSpeechEngineManager(
    private val registry: SpeechEngineRegistry
) {
    private var selectedStt: SpeechRecognitionEngine? = null
    private var selectedTts: SpeechSynthesisEngine? = null
    private var initialized = false

    @Synchronized
    fun select(selection: SpeechEngineSelection): Boolean {
        val stt = registry.getStt(selection.sttEngineId)
            ?: return false

        val tts = registry.getTts(selection.ttsEngineId)
            ?: return false

        if (
            selection.offlineOnly &&
            (!stt.isOffline || !tts.isOffline)
        ) {
            return false
        }

        release()

        selectedStt = stt
        selectedTts = tts

        return true
    }

    @Synchronized
    fun initializeSelected(): Boolean {
        if (initialized) return true

        val stt = selectedStt ?: return false
        val tts = selectedTts ?: return false

        if (!stt.initialize()) return false

        if (!tts.initialize()) {
            stt.release()
            return false
        }

        initialized = true
        return true
    }

    @Synchronized
    fun recognize(
        audio: PcmAudioInput
    ): SpeechEngineResult<String> {
        if (!initialized) {
            return SpeechEngineResult.Failure(
                "none",
                "Speech engines are not initialized."
            )
        }

        return selectedStt?.recognize(audio)
            ?: SpeechEngineResult.Failure(
                "none",
                "No STT engine selected."
            )
    }

    @Synchronized
    fun synthesize(
        text: String
    ): SpeechEngineResult<ByteArray> {
        if (!initialized) {
            return SpeechEngineResult.Failure(
                "none",
                "Speech engines are not initialized."
            )
        }

        return selectedTts?.synthesize(text)
            ?: SpeechEngineResult.Failure(
                "none",
                "No TTS engine selected."
            )
    }

    @Synchronized
    fun release() {
        selectedStt?.release()
        selectedTts?.release()
        initialized = false
    }

    fun selectedSttId(): String? = selectedStt?.engineId

    fun selectedTtsId(): String? = selectedTts?.engineId
}
