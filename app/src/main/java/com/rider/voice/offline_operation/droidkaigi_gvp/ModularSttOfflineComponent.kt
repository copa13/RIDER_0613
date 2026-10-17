package com.rider.voice.offline_operation.droidkaigi_gvp

import com.rider.voice.modular_engines.droidkaigi_gvp.Feature79ModularEngines
import com.rider.voice.modular_engines.droidkaigi_gvp.SpeechEngineSelection

class ModularSttOfflineComponent(
    private val engines: Feature79ModularEngines,
    private val selection: SpeechEngineSelection
) : OfflineComponent {

    override val componentId = "modular-stt"

    override val capabilities = setOf(
        OfflineCapability.SPEECH_RECOGNITION
    )

    @Volatile
    private var ready = false

    @Synchronized
    fun initialize(): Boolean {
        ready = engines.selectEngines(selection) &&
            engines.initialize()

        return ready
    }

    override fun isReady(): Boolean = ready

    override fun release() {
        engines.release()
        ready = false
    }
}
