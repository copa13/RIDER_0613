package com.rider.natural_language.droidkaigi_gvp

import com.rider.voice_commands.droidkaigi_gvp.VoiceCommand

class NaturalLanguageToVoiceCommand {

    fun convert(
        intent: NaturalLanguageIntent
    ): VoiceCommand? {

        return when (intent) {

            is NaturalLanguageIntent.OpenApp ->
                VoiceCommand.OpenApp(
                    intent.appName
                )

            is NaturalLanguageIntent.Call ->
                VoiceCommand.Call(
                    intent.contactName
                )

            is NaturalLanguageIntent.DraftMessage ->
                VoiceCommand.DraftMessage(
                    contactName = intent.contactName,
                    message = intent.message
                )

            is NaturalLanguageIntent.TextMessage ->
                VoiceCommand.TextMessage(
                    contactName = intent.contactName,
                    message = intent.message
                )

            is NaturalLanguageIntent.OpenWhatsAppMessage ->
                VoiceCommand.OpenWhatsAppMessage(
                    intent.contactName
                )

            is NaturalLanguageIntent.SetAlarm ->
                VoiceCommand.SetAlarm(
                    intent.timeText
                )

            is NaturalLanguageIntent.WakeAt ->
                VoiceCommand.WakeAt(
                    intent.timeText
                )

            is NaturalLanguageIntent.SetTimer ->
                VoiceCommand.SetTimer(
                    intent.durationText
                )

            NaturalLanguageIntent.LockPhone ->
                VoiceCommand.LockPhone

            NaturalLanguageIntent.OpenPowerMenu ->
                VoiceCommand.OpenPowerMenu

            NaturalLanguageIntent.BatteryStatus ->
                VoiceCommand.BatteryStatus

            NaturalLanguageIntent.GetTime ->
                VoiceCommand.GetTime

            NaturalLanguageIntent.GetDate ->
                VoiceCommand.GetDate

            NaturalLanguageIntent.CancelAlarm ->
                VoiceCommand.CancelAlarm

            NaturalLanguageIntent.ShowAlarms ->
                VoiceCommand.ShowAlarms

            NaturalLanguageIntent.StopTimer ->
                VoiceCommand.StopTimer

            NaturalLanguageIntent.SnoozeTimer ->
                VoiceCommand.SnoozeTimer

            NaturalLanguageIntent.TimeLeft ->
                VoiceCommand.TimeLeft

            NaturalLanguageIntent.PlayMusic ->
                VoiceCommand.PlayMusic

            NaturalLanguageIntent.PauseMusic ->
                VoiceCommand.PauseMusic

            NaturalLanguageIntent.NextSong ->
                VoiceCommand.NextSong

            NaturalLanguageIntent.PreviousSong ->
                VoiceCommand.PreviousSong

            NaturalLanguageIntent.StopMusic ->
                VoiceCommand.StopMusic

            NaturalLanguageIntent.VolumeUp ->
                VoiceCommand.VolumeUp

            NaturalLanguageIntent.VolumeDown ->
                VoiceCommand.VolumeDown

            NaturalLanguageIntent.Mute ->
                VoiceCommand.Mute

            NaturalLanguageIntent.Unmute ->
                VoiceCommand.Unmute

            NaturalLanguageIntent.FlashlightOn ->
                VoiceCommand.FlashlightOn

            NaturalLanguageIntent.FlashlightOff ->
                VoiceCommand.FlashlightOff

            NaturalLanguageIntent.FlashlightToggle ->
                VoiceCommand.FlashlightToggle

            NaturalLanguageIntent.GoBack ->
                VoiceCommand.GoBack

            NaturalLanguageIntent.GoHome ->
                VoiceCommand.GoHome

            NaturalLanguageIntent.RecentApps ->
                VoiceCommand.RecentApps

            NaturalLanguageIntent.Notifications ->
                VoiceCommand.Notifications

            NaturalLanguageIntent.QuickSettings ->
                VoiceCommand.QuickSettings

            NaturalLanguageIntent.ScrollDown ->
                VoiceCommand.ScrollDown

            NaturalLanguageIntent.ScrollUp ->
                VoiceCommand.ScrollUp

            NaturalLanguageIntent.TapConfirm ->
                VoiceCommand.TapConfirm

            NaturalLanguageIntent.TapCancel ->
                VoiceCommand.TapCancel

            NaturalLanguageIntent.WifiSettings ->
                VoiceCommand.WifiSettings

            NaturalLanguageIntent.BluetoothSettings ->
                VoiceCommand.BluetoothSettings

            NaturalLanguageIntent.BatterySettings ->
                VoiceCommand.BatterySettings

            NaturalLanguageIntent.AccessibilitySettings ->
                VoiceCommand.AccessibilitySettings

            NaturalLanguageIntent.NotificationSettings ->
                VoiceCommand.NotificationSettings

            NaturalLanguageIntent.LocationSettings ->
                VoiceCommand.LocationSettings

            NaturalLanguageIntent.HotspotSettings ->
                VoiceCommand.HotspotSettings

            NaturalLanguageIntent.StopListening ->
                VoiceCommand.StopListening

            NaturalLanguageIntent.PauseAssistant ->
                VoiceCommand.PauseAssistant

            NaturalLanguageIntent.ResumeAssistant ->
                VoiceCommand.ResumeAssistant

            NaturalLanguageIntent.Cancel ->
                VoiceCommand.Cancel

            NaturalLanguageIntent.Repeat ->
                VoiceCommand.Repeat

            NaturalLanguageIntent.WhatCanYouDo ->
                VoiceCommand.WhatCanYouDo
        }
    }
}
