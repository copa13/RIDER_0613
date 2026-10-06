package com.rider.command_confirmation.droidkaigi_gvp

import com.rider.voice_commands.droidkaigi_gvp.VoiceCommand
import com.rider.voice_commands.droidkaigi_gvp.VoiceCommandExecutor

class CommandConfirmationManager(
    private val executor: VoiceCommandExecutor,
    private val policy: CommandConfirmationPolicy = CommandConfirmationPolicy(),
    private val confirmationParser: CommandConfirmationParser =
        CommandConfirmationParser(),
    private val confirmationTimeoutMillis: Long = 30_000L,
    private val clock: () -> Long = { System.currentTimeMillis() }
) {

    private var pendingCommand: PendingCommand? = null
    private var status: ConfirmationStatus = ConfirmationStatus.IDLE

    fun submit(command: VoiceCommand): CommandConfirmationResult {
        clearExpired()

        if (!policy.requiresConfirmation(command)) {
            executor.execute(command)

            status = ConfirmationStatus.CONFIRMED

            return CommandConfirmationResult.Executed(
                PendingCommand(
                    command = command,
                    summary = commandSummary(command),
                    createdAtMillis = clock()
                )
            )
        }

        val pending = PendingCommand(
            command = command,
            summary = commandSummary(command),
            createdAtMillis = clock()
        )

        pendingCommand = pending
        status = ConfirmationStatus.WAITING_FOR_CONFIRMATION

        return CommandConfirmationResult.ConfirmationRequired(
            pendingCommand = pending
        )
    }

    fun handleConfirmation(
        text: String
    ): CommandConfirmationResult {

        clearExpired()

        val pending = pendingCommand
            ?: return CommandConfirmationResult.NoPendingCommand(
                message = "There is no command waiting for confirmation."
            )

        val decision = confirmationParser.parse(text)

        return when (decision) {

            ConfirmationDecision.YES -> {
                pendingCommand = null
                status = ConfirmationStatus.CONFIRMED

                val executed = executor.execute(
                    pending.command
                )

                if (executed) {
                    CommandConfirmationResult.Executed(
                        command = pending
                    )
                } else {
                    CommandConfirmationResult.Cancelled(
                        command = pending
                    )
                }
            }

            ConfirmationDecision.NO -> {
                pendingCommand = null
                status = ConfirmationStatus.CANCELLED

                CommandConfirmationResult.Cancelled(
                    command = pending
                )
            }

            ConfirmationDecision.UNKNOWN -> {
                CommandConfirmationResult.ConfirmationRequired(
                    pendingCommand = pending
                )
            }
        }
    }

    fun cancelPending(): CommandConfirmationResult {
        val pending = pendingCommand
            ?: return CommandConfirmationResult.NoPendingCommand(
                message = "There is no command waiting for confirmation."
            )

        pendingCommand = null
        status = ConfirmationStatus.CANCELLED

        return CommandConfirmationResult.Cancelled(
            command = pending
        )
    }

    fun hasPendingCommand(): Boolean {
        clearExpired()
        return pendingCommand != null
    }

    fun getPendingCommand(): PendingCommand? {
        clearExpired()
        return pendingCommand
    }

    fun getStatus(): ConfirmationStatus {
        clearExpired()
        return status
    }

    private fun clearExpired() {
        val pending = pendingCommand ?: return

        if (clock() - pending.createdAtMillis >
            confirmationTimeoutMillis
        ) {
            pendingCommand = null
            status = ConfirmationStatus.EXPIRED
        }
    }

    private fun commandSummary(
        command: VoiceCommand
    ): String {
        return when (command) {

            is VoiceCommand.DraftMessage ->
                "Draft a message to ${command.contact}"

            is VoiceCommand.TextMessage ->
                "Send a message to ${command.contact}"

            is VoiceCommand.OpenWhatsAppMessage ->
                "Open WhatsApp message for ${command.contact}"

            is VoiceCommand.Call ->
                "Call ${command.contact}"

            is VoiceCommand.CallEmergencyContact ->
                "Call emergency contact"

            is VoiceCommand.DialNumber ->
                "Dial ${command.number}"

            is VoiceCommand.SetAlarm ->
                "Set an alarm"

            is VoiceCommand.WakeAt ->
                "Set a wake-up alarm"

            is VoiceCommand.CancelAlarm ->
                "Cancel an alarm"

            is VoiceCommand.SetTimer ->
                "Set a timer"

            is VoiceCommand.StopTimer ->
                "Stop the timer"

            is VoiceCommand.SnoozeTimer ->
                "Snooze the timer"

            else ->
                "Execute the requested command"
        }
    }
}
