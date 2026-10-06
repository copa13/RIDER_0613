package com.rider.voice_commands.droidkaigi_gvp

sealed class VoiceCommand {

    // DEVICE
    data object LockPhone : VoiceCommand()
    data object OpenPowerMenu : VoiceCommand()
    data object BatteryStatus : VoiceCommand()
    data object GetTime : VoiceCommand()
    data object GetDate : VoiceCommand()

    // APPS
    data class OpenApp(val appName: String) : VoiceCommand()

    // CALLS
    data class Call(val contactName: String) : VoiceCommand()
    data object DialNumber : VoiceCommand()
    data object CallEmergencyContact : VoiceCommand()

    // MESSAGES
    data class DraftMessage(
        val contactName: String,
        val message: String
    ) : VoiceCommand()

    data class TextMessage(
        val contactName: String,
        val message: String
    ) : VoiceCommand()

    data class OpenWhatsAppMessage(
        val contactName: String
    ) : VoiceCommand()

    // ALARMS
    data class SetAlarm(val timeText: String) : VoiceCommand()
    data class WakeAt(val timeText: String) : VoiceCommand()
    data object CancelAlarm : VoiceCommand()
    data object ShowAlarms : VoiceCommand()

    // TIMERS
    data class SetTimer(val durationText: String) : VoiceCommand()
    data object StopTimer : VoiceCommand()
    data object SnoozeTimer : VoiceCommand()
    data object TimeLeft : VoiceCommand()

    // MEDIA
    data object PlayMusic : VoiceCommand()
    data object PauseMusic : VoiceCommand()
    data object NextSong : VoiceCommand()
    data object PreviousSong : VoiceCommand()
    data object StopMusic : VoiceCommand()

    // VOLUME
    data object VolumeUp : VoiceCommand()
    data object VolumeDown : VoiceCommand()
    data object Mute : VoiceCommand()
    data object Unmute : VoiceCommand()

    // FLASHLIGHT
    data object FlashlightOn : VoiceCommand()
    data object FlashlightOff : VoiceCommand()
    data object FlashlightToggle : VoiceCommand()

    // NAVIGATION
    data object GoBack : VoiceCommand()
    data object GoHome : VoiceCommand()
    data object RecentApps : VoiceCommand()
    data object Notifications : VoiceCommand()
    data object QuickSettings : VoiceCommand()
    data object ScrollDown : VoiceCommand()
    data object ScrollUp : VoiceCommand()
    data object TapConfirm : VoiceCommand()
    data object TapCancel : VoiceCommand()

    // SETTINGS
    data object WifiSettings : VoiceCommand()
    data object BluetoothSettings : VoiceCommand()
    data object BatterySettings : VoiceCommand()
    data object AccessibilitySettings : VoiceCommand()
    data object NotificationSettings : VoiceCommand()
    data object LocationSettings : VoiceCommand()
    data object HotspotSettings : VoiceCommand()

    // ASSISTANT
    data object StopListening : VoiceCommand()
    data object PauseAssistant : VoiceCommand()
    data object ResumeAssistant : VoiceCommand()
    data object Cancel : VoiceCommand()
    data object Repeat : VoiceCommand()
    data object WhatCanYouDo : VoiceCommand()
}
