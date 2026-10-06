package com.rider.natural_language.droidkaigi_gvp

sealed class NaturalLanguageIntent {

    data class OpenApp(
        val appName: String
    ) : NaturalLanguageIntent()

    data class Call(
        val contactName: String
    ) : NaturalLanguageIntent()

    data class DraftMessage(
        val contactName: String,
        val message: String
    ) : NaturalLanguageIntent()

    data class TextMessage(
        val contactName: String,
        val message: String
    ) : NaturalLanguageIntent()

    data class OpenWhatsAppMessage(
        val contactName: String
    ) : NaturalLanguageIntent()

    data class SetAlarm(
        val timeText: String
    ) : NaturalLanguageIntent()

    data class WakeAt(
        val timeText: String
    ) : NaturalLanguageIntent()

    data class SetTimer(
        val durationText: String
    ) : NaturalLanguageIntent()

    data object LockPhone : NaturalLanguageIntent()
    data object OpenPowerMenu : NaturalLanguageIntent()
    data object BatteryStatus : NaturalLanguageIntent()
    data object GetTime : NaturalLanguageIntent()
    data object GetDate : NaturalLanguageIntent()

    data object CancelAlarm : NaturalLanguageIntent()
    data object ShowAlarms : NaturalLanguageIntent()

    data object StopTimer : NaturalLanguageIntent()
    data object SnoozeTimer : NaturalLanguageIntent()
    data object TimeLeft : NaturalLanguageIntent()

    data object PlayMusic : NaturalLanguageIntent()
    data object PauseMusic : NaturalLanguageIntent()
    data object NextSong : NaturalLanguageIntent()
    data object PreviousSong : NaturalLanguageIntent()
    data object StopMusic : NaturalLanguageIntent()

    data object VolumeUp : NaturalLanguageIntent()
    data object VolumeDown : NaturalLanguageIntent()
    data object Mute : NaturalLanguageIntent()
    data object Unmute : NaturalLanguageIntent()

    data object FlashlightOn : NaturalLanguageIntent()
    data object FlashlightOff : NaturalLanguageIntent()
    data object FlashlightToggle : NaturalLanguageIntent()

    data object GoBack : NaturalLanguageIntent()
    data object GoHome : NaturalLanguageIntent()
    data object RecentApps : NaturalLanguageIntent()
    data object Notifications : NaturalLanguageIntent()
    data object QuickSettings : NaturalLanguageIntent()
    data object ScrollDown : NaturalLanguageIntent()
    data object ScrollUp : NaturalLanguageIntent()
    data object TapConfirm : NaturalLanguageIntent()
    data object TapCancel : NaturalLanguageIntent()

    data object WifiSettings : NaturalLanguageIntent()
    data object BluetoothSettings : NaturalLanguageIntent()
    data object BatterySettings : NaturalLanguageIntent()
    data object AccessibilitySettings : NaturalLanguageIntent()
    data object NotificationSettings : NaturalLanguageIntent()
    data object LocationSettings : NaturalLanguageIntent()
    data object HotspotSettings : NaturalLanguageIntent()

    data object StopListening : NaturalLanguageIntent()
    data object PauseAssistant : NaturalLanguageIntent()
    data object ResumeAssistant : NaturalLanguageIntent()
    data object Cancel : NaturalLanguageIntent()
    data object Repeat : NaturalLanguageIntent()
    data object WhatCanYouDo : NaturalLanguageIntent()
}
