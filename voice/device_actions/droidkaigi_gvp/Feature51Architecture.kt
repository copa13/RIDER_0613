package YOUR_EXISTING_PACKAGE

object Feature51Architecture {

    const val FEATURE_NUMBER = 51

    const val FEATURE_NAME = "Device Actions"

    const val PIPELINE =
        "Existing VoiceCommand -> DeviceAction -> AndroidDeviceActionExecutor -> Existing VoiceCommandExecutor"

    const val USES_EXISTING_VOICE_COMMANDS = true

    const val CREATES_NEW_COMMANDS = false

    const val DUPLICATES_COMMAND_REGISTRY = false

    const val REUSES_FEATURE_47_EXECUTOR = true

    const val REUSES_FEATURE_50_TOOL_LAYER = true

    const val OFFLINE_CAPABLE = true
}
