package YOUR_EXISTING_PACKAGE

import voice.commands.droidkaigi_gvp.VoiceCommand
import voice.commands.droidkaigi_gvp.VoiceCommandExecutor

class AndroidDeviceActionExecutor(
    private val voiceCommandExecutor: VoiceCommandExecutor
) {

    fun execute(action: DeviceAction): DeviceActionResult {
        return executeCommand(action.command)
    }

    fun executeCommand(command: VoiceCommand): DeviceActionResult {

        return try {

            val executed = voiceCommandExecutor.execute(command)

            if (executed) {
                DeviceActionResult.Success(
                    command = command::class.simpleName ?: "VoiceCommand"
                )
            } else {
                DeviceActionResult.Failed(
                    command = command::class.simpleName ?: "VoiceCommand",
                    reason = "Android device action was not executed"
                )
            }

        } catch (exception: Exception) {

            DeviceActionResult.Failed(
                command = command::class.simpleName ?: "VoiceCommand",
                reason = exception.message
                    ?: "Unknown Android execution error"
            )
        }
    }
}
