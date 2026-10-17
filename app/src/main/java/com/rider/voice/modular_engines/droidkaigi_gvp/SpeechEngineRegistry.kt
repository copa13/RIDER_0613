package com.rider.voice.modular_engines.droidkaigi_gvp

class SpeechEngineRegistry {

    private val sttEngines =
        linkedMapOf<String, SpeechRecognitionEngine>()

    private val ttsEngines =
        linkedMapOf<String, SpeechSynthesisEngine>()

    @Synchronized
    fun registerStt(engine: SpeechRecognitionEngine) {
        require(engine.engineId.isNotBlank())
        sttEngines[engine.engineId] = engine
    }

    @Synchronized
    fun registerTts(engine: SpeechSynthesisEngine) {
        require(engine.engineId.isNotBlank())
        ttsEngines[engine.engineId] = engine
    }

    @Synchronized
    fun getStt(
        id: String
    ): SpeechRecognitionEngine? = sttEngines[id]

    @Synchronized
    fun getTts(
        id: String
    ): SpeechSynthesisEngine? = ttsEngines[id]

    @Synchronized
    fun listStt(): List<SpeechEngineDescriptor> =
        sttEngines.values.map {
            SpeechEngineDescriptor(
                engineId = it.engineId,
                type = SpeechEngineType.STT,
                offline = it.isOffline
            )
        }

    @Synchronized
    fun listTts(): List<SpeechEngineDescriptor> =
        ttsEngines.values.map {
            SpeechEngineDescriptor(
                engineId = it.engineId,
                type = SpeechEngineType.TTS,
                offline = it.isOffline
            )
        }
}
