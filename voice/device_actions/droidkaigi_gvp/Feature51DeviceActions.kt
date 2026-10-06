package YOUR_EXISTING_PACKAGE

import voice.commands.droidkaigi_gvp.VoiceCommand
import voice.commands.droidkaigi_gvp.VoiceCommandExecutor

class Feature51DeviceActions(
    voiceCommandExecutor: VoiceCommandExecutor
) {

    private val executor = AndroidDeviceActionExecutor(
        voiceCommandExecutor = voiceCommandExecutor
    )

    fun execute(command: VoiceCommand): DeviceActionResult {
        return executor.executeCommand(command)
    }

    fun execute(action: DeviceAction): DeviceActionResult {
        return executor.execute(action)
    }
}
