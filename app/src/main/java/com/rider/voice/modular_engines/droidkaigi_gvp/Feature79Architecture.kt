package com.rider.voice.modular_engines.droidkaigi_gvp

object Feature79Architecture {
    const val FEATURE_NUMBER = 79
    const val FEATURE_NAME = "Modular STT/TTS Engines"

    const val SEPARATE_STT_INTERFACE = true
    const val SEPARATE_TTS_INTERFACE = true
    const val ENGINE_REGISTRY = true
    const val RUNTIME_ENGINE_SELECTION = true
    const val OFFLINE_ONLY_SELECTION = true

    const val SHERPA_ONNX_STT_ADAPTER = true
    const val SHERPA_ONNX_TTS_ADAPTER = true
    const val PCM_WAV_OUTPUT = true
}
