package com.rider.tool_calling.droidkaigi_gvp

object Feature50Architecture {

    const val FEATURE_NUMBER = 50

    const val FEATURE_NAME = "Tool / Function Calling"

    const val PIPELINE =
        "Existing VoiceCommand -> ToolCall Adapter -> Existing VoiceCommandExecutor"

    const val USES_EXISTING_COMMANDS = true

    const val CREATES_NEW_COMMANDS = false

    const val DUPLICATES_COMMAND_REGISTRY = false

    const val REUSES_FEATURE_47_EXECUTOR = true

    const val OFFLINE = true
}
