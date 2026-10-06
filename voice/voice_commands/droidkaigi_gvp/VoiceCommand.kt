package com.rider.voice_commands.droidkaigi_gvp

sealed class VoiceCommand {

    data class OpenApp(
        val appName: String
    ) : VoiceCommand()

    data object VolumeUp : VoiceCommand()

    data object VolumeDown : VoiceCommand()

    data object Mute : VoiceCommand()

    data object FlashlightOn : VoiceCommand()

    data object FlashlightOff : VoiceCommand()

    data object LockPhone : VoiceCommand()

    data object OpenSettings : VoiceCommand()

    data object OpenPowerMenu : VoiceCommand()

    data object GoBack : VoiceCommand()

    data object GoHome : VoiceCommand()

    data object OpenRecents : VoiceCommand()

    data object OpenNotifications : VoiceCommand()

    data object OpenQuickSettings : VoiceCommand()

    data class Call(
        val contactName: String
    ) : VoiceCommand()

    data class MessageDraft(
        val contactName: String,
        val message: String
    ) : VoiceCommand()

    data class SetTimer(
        val durationMs: Long
    ) : VoiceCommand()

    data class SetAlarm(
        val triggerAtMillis: Long
    ) : VoiceCommand()

    data object Stop : VoiceCommand()
}
