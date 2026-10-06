package com.rider.command_confirmation.droidkaigi_gvp

import com.rider.voice_commands.droidkaigi_gvp.VoiceCommand
import com.rider.voice_commands.droidkaigi_gvp.VoiceCommandExecutor

class Feature49CommandConfirmation(
    executor: VoiceCommandExecutor
) {

    private val manager = CommandConfirmationManager(
        executor = executor
    )

    fun submitCommand(
        command: VoiceCommand
    ): CommandConfirmationResult {
        return manager.submit(command)
    }

    fun processConfirmation(
        text: String
    ): CommandConfirmationResult {
        return manager.handleConfirmation(text)
    }

    fun cancel(): CommandConfirmationResult {
        return manager.cancelPending()
    }

    fun hasPendingConfirmation(): Boolean {
        return manager.hasPendingCommand()
    }

    fun getPendingCommand(): PendingCommand? {
        return manager.getPendingCommand()
    }

    fun getStatus(): ConfirmationStatus {
        return manager.getStatus()
    }
}
