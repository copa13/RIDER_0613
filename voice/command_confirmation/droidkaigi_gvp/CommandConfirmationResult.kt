package com.rider.command_confirmation.droidkaigi_gvp

sealed class CommandConfirmationResult {

    data class ConfirmationRequired(
        val pendingCommand: PendingCommand
    ) : CommandConfirmationResult()

    data class Executed(
        val command: PendingCommand
    ) : CommandConfirmationResult()

    data class Cancelled(
        val command: PendingCommand
    ) : CommandConfirmationResult()

    data class NoPendingCommand(
        val message: String
    ) : CommandConfirmationResult()

    data class Expired(
        val command: PendingCommand
    ) : CommandConfirmationResult()
}
