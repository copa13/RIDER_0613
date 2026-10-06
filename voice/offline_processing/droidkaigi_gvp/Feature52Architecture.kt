package com.rider.offline_processing.droidkaigi_gvp

object Feature52Architecture {

    const val FEATURE_NUMBER = 52

    const val FEATURE_NAME = "Offline Processing"

    const val PIPELINE =
        "Existing VoiceCommand -> Feature 50 Tool Calling -> Feature 52 Offline Processing -> Feature 51 Device Actions"

    const val CREATES_NEW_COMMANDS = false

    const val DUPLICATES_COMMAND_REGISTRY = false

    const val REUSES_FEATURE_48 = true

    const val REUSES_FEATURE_49 = true

    const val REUSES_FEATURE_50 = true

    const val REUSES_FEATURE_51 = true

    const val REQUIRES_INTERNET = false

    const val OFFLINE_FIRST = true
}
