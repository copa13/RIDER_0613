package com.rider.natural_language.droidkaigi_gvp

object Feature48Architecture {

    const val FEATURE_NUMBER = 48

    const val FEATURE_NAME =
        "Natural-language commands"

    const val PIPELINE =
        "Speech -> Normalize -> Intent Detection -> " +
        "Confidence -> Validation -> VoiceCommand -> " +
        "Existing #47 Executor -> Android Action"

    const val OFFLINE_DESIGN = true

    const val USES_EXISTING_FEATURE_47 = true

    const val REPLACES_FEATURE_47 = false
}
