package com.rider.voice.error_fallback.droidkaigi_gvp

enum class ErrorType {
    VAD,
    STT,
    TTS,
    MODEL,
    DOWNLOAD,
    COMMAND,
    ACTION,
    TIMEOUT,
    PERMISSION,
    UNKNOWN
}
