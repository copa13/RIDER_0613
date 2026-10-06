package com.rider.command_confirmation.droidkaigi_gvp

import com.rider.voice_commands.droidkaigi_gvp.VoiceCommand

data class PendingCommand(
    val command: VoiceCommand,
    val summary: String,
    val createdAtMillis: Long
)
