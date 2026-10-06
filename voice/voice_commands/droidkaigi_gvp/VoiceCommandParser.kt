package com.rider.voice_commands.droidkaigi_gvp

class VoiceCommandParser {

    fun parse(input: String): VoiceCommand? {
        val text = input
            .trim()
            .lowercase()
            .replace(Regex("\\s+"), " ")

        if (text.isEmpty()) return null

        // DEVICE
        when (text) {
            "lock phone" -> return VoiceCommand.LockPhone
            "open power menu" -> return VoiceCommand.OpenPowerMenu
            "battery status" -> return VoiceCommand.BatteryStatus
            "what time is it" -> return VoiceCommand.GetTime
            "what is the date" -> return VoiceCommand.GetDate
        }

        // APPS
        if (text.startsWith("open ")) {
            val appName = text.removePrefix("open ").trim()

            return when (appName) {
                "chrome" -> VoiceCommand.OpenApp("Chrome")
                "whatsapp" -> VoiceCommand.OpenApp("WhatsApp")
                "youtube" -> VoiceCommand.OpenApp("YouTube")
                "camera" -> VoiceCommand.OpenApp("Camera")
                "settings" -> VoiceCommand.OpenApp("Settings")
                "calculator" -> VoiceCommand.OpenApp("Calculator")
                "phone" -> VoiceCommand.OpenApp("Phone")
                "messages" -> VoiceCommand.OpenApp("Messages")
                "notifications" -> VoiceCommand.Notifications
                "quick settings" -> VoiceCommand.QuickSettings
                else -> null
            }?.let { return it }
        }

        // CALLS
        if (text == "dial number") {
            return VoiceCommand.DialNumber
        }

        if (text == "call emergency contact") {
            return VoiceCommand.CallEmergencyContact
        }

        if (text.startsWith("call ")) {
            val contact = input
                .trim()
                .removePrefix("call ")
                .trim()

            if (contact.isNotEmpty()) {
                return VoiceCommand.Call(contact)
            }
        }

        // MESSAGES
        Regex(
            "^draft message to (.+?) saying (.+)$",
            RegexOption.IGNORE_CASE
        ).matchEntire(input.trim())?.let {
            return VoiceCommand.DraftMessage(
                contactName = it.groupValues[1].trim(),
                message = it.groupValues[2].trim()
            )
        }

        Regex(
            "^text (.+?) saying (.+)$",
            RegexOption.IGNORE_CASE
        ).matchEntire(input.trim())?.let {
            return VoiceCommand.TextMessage(
                contactName = it.groupValues[1].trim(),
                message = it.groupValues[2].trim()
            )
        }

        Regex(
            "^open whatsapp message to (.+)$",
            RegexOption.IGNORE_CASE
        ).matchEntire(input.trim())?.let {
            return VoiceCommand.OpenWhatsAppMessage(
                contactName = it.groupValues[1].trim()
            )
        }

        // ALARMS
        Regex(
            "^set alarm for (.+)$",
            RegexOption.IGNORE_CASE
        ).matchEntire(input.trim())?.let {
            return VoiceCommand.SetAlarm(it.groupValues[1].trim())
        }

        Regex(
            "^wake me at (.+)$",
            RegexOption.IGNORE_CASE
        ).matchEntire(input.trim())?.let {
            return VoiceCommand.WakeAt(it.groupValues[1].trim())
        }

        when (text) {
            "cancel alarm" -> return VoiceCommand.CancelAlarm
            "show alarms" -> return VoiceCommand.ShowAlarms
        }

        // TIMERS
        Regex(
            "^set timer for (.+)$",
            RegexOption.IGNORE_CASE
        ).matchEntire(input.trim())?.let {
            return VoiceCommand.SetTimer(it.groupValues[1].trim())
        }

        when (text) {
            "stop timer" -> return VoiceCommand.StopTimer
            "snooze timer" -> return VoiceCommand.SnoozeTimer
            "how much time left" -> return VoiceCommand.TimeLeft
        }

        // MEDIA
        when (text) {
            "play music" -> return VoiceCommand.PlayMusic
            "pause music" -> return VoiceCommand.PauseMusic
            "next song" -> return VoiceCommand.NextSong
            "previous song" -> return VoiceCommand.PreviousSong
            "stop music" -> return VoiceCommand.StopMusic
        }

        // VOLUME
        when (text) {
            "volume up" -> return VoiceCommand.VolumeUp
            "volume down" -> return VoiceCommand.VolumeDown
            "mute" -> return VoiceCommand.Mute
            "unmute" -> return VoiceCommand.Unmute
        }

        // FLASHLIGHT
        when (text) {
            "turn on flashlight" -> return VoiceCommand.FlashlightOn
            "turn off flashlight" -> return VoiceCommand.FlashlightOff
            "toggle flashlight" -> return VoiceCommand.FlashlightToggle
        }

        // NAVIGATION
        when (text) {
            "go back" -> return VoiceCommand.GoBack
            "go home" -> return VoiceCommand.GoHome
            "show recent apps" -> return VoiceCommand.RecentApps
            "open notifications" -> return VoiceCommand.Notifications
            "open quick settings" -> return VoiceCommand.QuickSettings
            "scroll down" -> return VoiceCommand.ScrollDown
            "scroll up" -> return VoiceCommand.ScrollUp
            "tap confirm" -> return VoiceCommand.TapConfirm
            "tap cancel" -> return VoiceCommand.TapCancel
        }

        // SETTINGS
        when (text) {
            "open wi-fi settings",
            "open wifi settings" ->
                return VoiceCommand.WifiSettings

            "open bluetooth settings" ->
                return VoiceCommand.BluetoothSettings

            "open battery settings" ->
                return VoiceCommand.BatterySettings

            "open accessibility settings" ->
                return VoiceCommand.AccessibilitySettings

            "open notification settings" ->
                return VoiceCommand.NotificationSettings

            "open location settings" ->
                return VoiceCommand.LocationSettings

            "open hotspot settings" ->
                return VoiceCommand.HotspotSettings
        }

        // ASSISTANT
        when (text) {
            "stop listening" -> return VoiceCommand.StopListening
            "pause alex" -> return VoiceCommand.PauseAssistant
            "resume alex" -> return VoiceCommand.ResumeAssistant
            "cancel" -> return VoiceCommand.Cancel
            "repeat" -> return VoiceCommand.Repeat
            "what can you do" -> return VoiceCommand.WhatCanYouDo
        }

        return null
    }
}
