package com.rider.voice.modular_engines.droidkaigi_gvp

class Feature79ModularEngines {

    val registry = SpeechEngineRegistry()

    private val manager = ModularSpeechEngineManager(registry)

    fun registerStt(engine: SpeechRecognitionEngine) {
        registry.registerStt(engine)
    }

    fun registerTts(engine: SpeechSynthesisEngine) {
        registry.registerTts(engine)
    }

    fun selectEngines(
        selection: SpeechEngineSelection
    ): Boolean = manager.select(selection)

    fun initialize(): Boolean =
        manager.initializeSelected()

    fun recognize(
        audio: PcmAudioInput
    ): SpeechEngineResult<String> =
        manager.recognize(audio)

    fun synthesize(
        text: String
    ): SpeechEngineResult<ByteArray> =
        manager.synthesize(text)

    fun availableSttEngines(): List<SpeechEngineDescriptor> =
        registry.listStt()

    fun availableTtsEngines(): List<SpeechEngineDescriptor> =
        registry.listTts()

    fun release() {
        manager.release()
    }
}
