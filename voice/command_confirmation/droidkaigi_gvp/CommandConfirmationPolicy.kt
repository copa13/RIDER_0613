package com.rider.command_confirmation.droidkaigi_gvp

import com.rider.voice_commands.droidkaigi_gvp.VoiceCommand

class CommandConfirmationPolicy {

    fun requiresConfirmation(command: VoiceCommand): Boolean {
        return when (command) {

            is VoiceCommand.DraftMessage -> true
            is VoiceCommand.TextMessage -> true
            is VoiceCommand.OpenWhatsAppMessage -> true

            is VoiceCommand.Call -> true
            is VoiceCommand.CallEmergencyContact -> true
            is VoiceCommand.DialNumber -> true

            is VoiceCommand.SetAlarm -> true
            is VoiceCommand.WakeAt -> true
            is VoiceCommand.CancelAlarm -> true

            is VoiceCommand.SetTimer -> true
            is VoiceCommand.StopTimer -> true
            is VoiceCommand.SnoozeTimer -> true

            else -> false
        }
    }
}
