package com.rider.voice.latency_measurement.droidkaigi_gvp

enum class LatencyStage {
    VAD,
    STT,
    COMMAND_PROCESSING,
    ACTION,
    TTS_START,
    END_TO_END
}
