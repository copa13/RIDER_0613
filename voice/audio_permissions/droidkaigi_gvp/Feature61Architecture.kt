package com.rider.audio_permissions.droidkaigi_gvp

object Feature61Architecture {

    const val FEATURE_NUMBER = 61

    const val FEATURE_NAME = "Audio Permissions Handling"

    const val PRIMARY_PERMISSION =
        "android.permission.RECORD_AUDIO"

    const val HANDLES_PERMISSION_CHECK = true

    const val HANDLES_RUNTIME_REQUEST = true

    const val HANDLES_DENIED_STATE = true

    const val HANDLES_BLOCKED_STATE = true

    const val PROTECTS_VOICE_PIPELINE = true

    const val CREATES_NEW_COMMANDS = false

    const val DUPLICATES_VOICE_COMMANDS = false

    const val OFFLINE_CAPABLE = true
}
