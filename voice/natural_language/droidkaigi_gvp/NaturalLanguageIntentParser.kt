package com.rider.natural_language.droidkaigi_gvp

class NaturalLanguageIntentParser(
    private val normalizer: NaturalLanguageNormalizer =
        NaturalLanguageNormalizer()
) {

    fun parse(input: String): NaturalLanguageResult? {

        val normalized =
            normalizer.normalize(input)

        if (normalized.isEmpty()) {
            return null
        }

        val direct =
            parseDirectCommand(normalized)

        if (direct != null) {
            return NaturalLanguageResult(
                intent = direct,
                confidence = 1.0f,
                source = NaturalLanguageResult.Source.EXACT_COMMAND
            )
        }

        val natural =
            normalizer.removePoliteness(normalized)

        return parseNaturalLanguage(natural)
    }

    private fun parseDirectCommand(
        text: String
    ): NaturalLanguageIntent? {

        return when (text) {

            "lock phone" ->
                NaturalLanguageIntent.LockPhone

            "open power menu" ->
                NaturalLanguageIntent.OpenPowerMenu

            "battery status" ->
                NaturalLanguageIntent.BatteryStatus

            "what time is it" ->
                NaturalLanguageIntent.GetTime

            "what is the date" ->
                NaturalLanguageIntent.GetDate

            "cancel alarm" ->
                NaturalLanguageIntent.CancelAlarm

            "show alarms" ->
                NaturalLanguageIntent.ShowAlarms

            "stop timer" ->
                NaturalLanguageIntent.StopTimer

            "snooze timer" ->
                NaturalLanguageIntent.SnoozeTimer

            "how much time left" ->
                NaturalLanguageIntent.TimeLeft

            "play music" ->
                NaturalLanguageIntent.PlayMusic

            "pause music" ->
                NaturalLanguageIntent.PauseMusic

            "next song" ->
                NaturalLanguageIntent.NextSong

            "previous song" ->
                NaturalLanguageIntent.PreviousSong

            "stop music" ->
                NaturalLanguageIntent.StopMusic

            "volume up" ->
                NaturalLanguageIntent.VolumeUp

            "volume down" ->
                NaturalLanguageIntent.VolumeDown

            "mute" ->
                NaturalLanguageIntent.Mute

            "unmute" ->
                NaturalLanguageIntent.Unmute

            "turn on flashlight" ->
                NaturalLanguageIntent.FlashlightOn

            "turn off flashlight" ->
                NaturalLanguageIntent.FlashlightOff

            "toggle flashlight" ->
                NaturalLanguageIntent.FlashlightToggle

            "go back" ->
                NaturalLanguageIntent.GoBack

            "go home" ->
                NaturalLanguageIntent.GoHome

            "show recent apps" ->
                NaturalLanguageIntent.RecentApps

            "open notifications" ->
                NaturalLanguageIntent.Notifications

            "open quick settings" ->
                NaturalLanguageIntent.QuickSettings

            "scroll down" ->
                NaturalLanguageIntent.ScrollDown

            "scroll up" ->
                NaturalLanguageIntent.ScrollUp

            "tap confirm" ->
                NaturalLanguageIntent.TapConfirm

            "tap cancel" ->
                NaturalLanguageIntent.TapCancel

            "open wifi settings",
            "open wi-fi settings" ->
                NaturalLanguageIntent.WifiSettings

            "open bluetooth settings" ->
                NaturalLanguageIntent.BluetoothSettings

            "open battery settings" ->
                NaturalLanguageIntent.BatterySettings

            "open accessibility settings" ->
                NaturalLanguageIntent.AccessibilitySettings

            "open notification settings" ->
                NaturalLanguageIntent.NotificationSettings

            "open location settings" ->
                NaturalLanguageIntent.LocationSettings

            "open hotspot settings" ->
                NaturalLanguageIntent.HotspotSettings

            "stop listening" ->
                NaturalLanguageIntent.StopListening

            "pause alex" ->
                NaturalLanguageIntent.PauseAssistant

            "resume alex" ->
                NaturalLanguageIntent.ResumeAssistant

            "cancel" ->
                NaturalLanguageIntent.Cancel

            "repeat" ->
                NaturalLanguageIntent.Repeat

            "what can you do" ->
                NaturalLanguageIntent.WhatCanYouDo

            else -> null
        }
    }

    private fun parseNaturalLanguage(
        text: String
    ): NaturalLanguageResult? {

        parseOpenApp(text)?.let {
            return result(it, 0.96f)
        }

        parseCall(text)?.let {
            return result(it, 0.95f)
        }

        parseDraftMessage(text)?.let {
            return result(it, 0.94f)
        }

        parseTextMessage(text)?.let {
            return result(it, 0.94f)
        }

        parseWhatsAppMessage(text)?.let {
            return result(it, 0.94f)
        }

        parseAlarm(text)?.let {
            return result(it, 0.93f)
        }

        parseWake(text)?.let {
            return result(it, 0.93f)
        }

        parseTimer(text)?.let {
            return result(it, 0.93f)
        }

        if (containsAny(
                text,
                "lock my phone",
                "lock the phone",
                "lock this phone"
            )
        ) {
            return result(
                NaturalLanguageIntent.LockPhone,
                0.92f
            )
        }

        if (containsAny(
                text,
                "show power menu",
                "bring up power menu",
                "open the power menu"
            )
        ) {
            return result(
                NaturalLanguageIntent.OpenPowerMenu,
                0.92f
            )
        }

        if (containsAny(
                text,
                "check battery",
                "how much battery",
                "battery percentage",
                "how much charge"
            )
        ) {
            return result(
                NaturalLanguageIntent.BatteryStatus,
                0.92f
            )
        }

        if (containsAny(
                text,
                "tell me the time",
                "what's the time",
                "tell me what time it is"
            )
        ) {
            return result(
                NaturalLanguageIntent.GetTime,
                0.92f
            )
        }

        if (containsAny(
                text,
                "tell me today's date",
                "tell me the date",
                "what date is it",
                "which date is it"
            )
        ) {
            return result(
                NaturalLanguageIntent.GetDate,
                0.92f
            )
        }

        if (containsAny(
                text,
                "cancel my alarm",
                "remove the alarm",
                "delete the alarm"
            )
        ) {
            return result(
                NaturalLanguageIntent.CancelAlarm,
                0.91f
            )
        }

        if (containsAny(
                text,
                "show my alarms",
                "what alarms do i have",
                "list my alarms"
            )
        ) {
            return result(
                NaturalLanguageIntent.ShowAlarms,
                0.91f
            )
        }

        if (containsAny(
                text,
                "stop the timer",
                "cancel the timer",
                "end the timer"
            )
        ) {
            return result(
                NaturalLanguageIntent.StopTimer,
                0.91f
            )
        }

        if (containsAny(
                text,
                "snooze the timer",
                "snooze my timer"
            )
        ) {
            return result(
                NaturalLanguageIntent.SnoozeTimer,
                0.91f
            )
        }

        if (containsAny(
                text,
                "how long is left",
                "how much time remains",
                "how much timer is left",
                "how much time remains on the timer"
            )
        ) {
            return result(
                NaturalLanguageIntent.TimeLeft,
                0.91f
            )
        }

        if (containsAny(
                text,
                "start music",
                "start playing music",
                "play some music",
                "put on some music"
            )
        ) {
            return result(
                NaturalLanguageIntent.PlayMusic,
                0.90f
            )
        }

        if (containsAny(
                text,
                "pause the music",
                "pause my music",
                "stop playing for now"
            )
        ) {
            return result(
                NaturalLanguageIntent.PauseMusic,
                0.90f
            )
        }

        if (containsAny(
                text,
                "play the next song",
                "skip this song",
                "skip to next song"
            )
        ) {
            return result(
                NaturalLanguageIntent.NextSong,
                0.90f
            )
        }

        if (containsAny(
                text,
                "play the previous song",
                "go to previous song",
                "go back one song"
            )
        ) {
            return result(
                NaturalLanguageIntent.PreviousSong,
                0.90f
            )
        }

        if (containsAny(
                text,
                "stop the music",
                "stop playing music"
            )
        ) {
            return result(
                NaturalLanguageIntent.StopMusic,
                0.90f
            )
        }

        if (containsAny(
                text,
                "make the volume louder",
                "increase the volume",
                "turn the volume up",
                "raise the volume"
            )
        ) {
            return result(
                NaturalLanguageIntent.VolumeUp,
                0.90f
            )
        }

        if (containsAny(
                text,
                "make the volume lower",
                "decrease the volume",
                "turn the volume down",
                "lower the volume"
            )
        ) {
            return result(
                NaturalLanguageIntent.VolumeDown,
                0.90f
            )
        }

        if (containsAny(
                text,
                "silence the phone",
                "make it silent",
                "mute the phone",
                "turn sound off"
            )
        ) {
            return result(
                NaturalLanguageIntent.Mute,
                0.90f
            )
        }

        if (containsAny(
                text,
                "turn sound back on",
                "unmute the phone",
                "restore sound"
            )
        ) {
            return result(
                NaturalLanguageIntent.Unmute,
                0.90f
            )
        }

        if (containsAny(
                text,
                "turn the flashlight on",
                "switch on flashlight",
                "enable flashlight"
            )
        ) {
            return result(
                NaturalLanguageIntent.FlashlightOn,
                0.90f
            )
        }

        if (containsAny(
                text,
                "turn the flashlight off",
                "switch off flashlight",
                "disable flashlight"
            )
        ) {
            return result(
                NaturalLanguageIntent.FlashlightOff,
                0.90f
            )
        }

        if (containsAny(
                text,
                "switch flashlight",
                "change flashlight state"
            )
        ) {
            return result(
                NaturalLanguageIntent.FlashlightToggle,
                0.88f
            )
        }

        if (containsAny(
                text,
                "go to previous screen",
                "take me back",
                "return to previous screen"
            )
        ) {
            return result(
                NaturalLanguageIntent.GoBack,
                0.90f
            )
        }

        if (containsAny(
                text,
                "go to home screen",
                "take me home",
                "return home"
            )
        ) {
            return result(
                NaturalLanguageIntent.GoHome,
                0.90f
            )
        }

        if (containsAny(
                text,
                "show running apps",
                "show open apps",
                "show my recent applications"
            )
        ) {
            return result(
                NaturalLanguageIntent.RecentApps,
                0.89f
            )
        }

        if (containsAny(
                text,
                "show notifications",
                "let me see notifications",
                "bring up notifications"
            )
        ) {
            return result(
                NaturalLanguageIntent.Notifications,
                0.90f
            )
        }

        if (containsAny(
                text,
                "bring up quick settings",
                "show quick settings",
                "open quick panel"
            )
        ) {
            return result(
                NaturalLanguageIntent.QuickSettings,
                0.90f
            )
        }

        if (containsAny(
                text,
                "move down",
                "scroll lower",
                "go further down"
            )
        ) {
            return result(
                NaturalLanguageIntent.ScrollDown,
                0.86f
            )
        }

        if (containsAny(
                text,
                "move up",
                "scroll higher",
                "go further up"
            )
        ) {
            return result(
                NaturalLanguageIntent.ScrollUp,
                0.86f
            )
        }

        if (containsAny(
                text,
                "confirm this",
                "press confirm",
                "select confirm"
            )
        ) {
            return result(
                NaturalLanguageIntent.TapConfirm,
                0.89f
            )
        }

        if (containsAny(
                text,
                "cancel this",
                "press cancel",
                "select cancel"
            )
        ) {
            return result(
                NaturalLanguageIntent.TapCancel,
                0.89f
            )
        }

        if (containsAny(
                text,
                "open wifi settings",
                "show wifi settings",
                "take me to wifi settings"
            )
        ) {
            return result(
                NaturalLanguageIntent.WifiSettings,
                0.91f
            )
        }

        if (containsAny(
                text,
                "open bluetooth settings",
                "show bluetooth settings",
                "take me to bluetooth settings"
            )
        ) {
            return result(
                NaturalLanguageIntent.BluetoothSettings,
                0.91f
            )
        }

        if (containsAny(
                text,
                "open battery settings",
                "show battery settings"
            )
        ) {
            return result(
                NaturalLanguageIntent.BatterySettings,
                0.91f
            )
        }

        if (containsAny(
                text,
                "open accessibility settings",
                "show accessibility settings"
            )
        ) {
            return result(
                NaturalLanguageIntent.AccessibilitySettings,
                0.91f
            )
        }

        if (containsAny(
                text,
                "open notification settings",
                "show notification settings"
            )
        ) {
            return result(
                NaturalLanguageIntent.NotificationSettings,
                0.91f
            )
        }

        if (containsAny(
                text,
                "open location settings",
                "show location settings"
            )
        ) {
            return result(
                NaturalLanguageIntent.LocationSettings,
                0.91f
            )
        }

        if (containsAny(
                text,
                "open hotspot settings",
                "show hotspot settings",
                "take me to hotspot settings"
            )
        ) {
            return result(
                NaturalLanguageIntent.HotspotSettings,
                0.91f
            )
        }

        if (containsAny(
                text,
                "stop listening now",
                "don't listen",
                "stop listening to me"
            )
        ) {
            return result(
                NaturalLanguageIntent.StopListening,
                0.90f
            )
        }

        if (containsAny(
                text,
                "pause yourself",
                "pause the assistant"
            )
        ) {
            return result(
                NaturalLanguageIntent.PauseAssistant,
                0.88f
            )
        }

        if (containsAny(
                text,
                "continue listening",
                "start listening again",
                "resume the assistant"
            )
        ) {
            return result(
                NaturalLanguageIntent.ResumeAssistant,
                0.88f
            )
        }

        if (containsAny(
                text,
                "never mind",
                "forget that",
                "forget it"
            )
        ) {
            return result(
                NaturalLanguageIntent.Cancel,
                0.86f
            )
        }

        if (containsAny(
                text,
                "say that again",
                "tell me again",
                "say it again"
            )
        ) {
            return result(
                NaturalLanguageIntent.Repeat,
                0.90f
            )
        }

        if (containsAny(
                text,
                "what are your capabilities",
                "what can you help me with",
                "what commands do you know"
            )
        ) {
            return result(
                NaturalLanguageIntent.WhatCanYouDo,
                0.90f
            )
        }

        return null
    }

    private fun parseOpenApp(
        text: String
    ): NaturalLanguageIntent? {

        val patterns = listOf(
            Regex("^open (.+)$"),
            Regex("^launch (.+)$"),
            Regex("^start (.+)$"),
            Regex("^run (.+)$")
        )

        for (pattern in patterns) {
            val match = pattern.matchEntire(text)
                ?: continue

            val appName =
                match.groupValues[1]
                    .trim()

            if (appName.isNotEmpty()) {
                return NaturalLanguageIntent.OpenApp(
                    normalizeAppName(appName)
                )
            }
        }

        return null
    }

    private fun parseCall(
        text: String
    ): NaturalLanguageIntent? {

        val patterns = listOf(
            Regex("^call (.+)$"),
            Regex("^phone (.+)$"),
            Regex("^ring (.+)$"),
            Regex("^call up (.+)$")
        )

        for (pattern in patterns) {
            val match = pattern.matchEntire(text)
                ?: continue

            val name =
                match.groupValues[1].trim()

            if (name.isNotEmpty()) {
                return NaturalLanguageIntent.Call(name)
            }
        }

        return null
    }

    private fun parseDraftMessage(
        text: String
    ): NaturalLanguageIntent? {

        val patterns = listOf(
            Regex(
                "^write a message to (.+?) saying (.+)$"
            ),
            Regex(
                "^draft a message to (.+?) saying (.+)$"
            ),
            Regex(
                "^prepare a message for (.+?) saying (.+)$"
            ),
            Regex(
                "^compose a message to (.+?) saying (.+)$"
            )
        )

        for (pattern in patterns) {
            val match = pattern.matchEntire(text)
                ?: continue

            return NaturalLanguageIntent.DraftMessage(
                contactName = match.groupValues[1].trim(),
                message = match.groupValues[2].trim()
            )
        }

        return null
    }

    private fun parseTextMessage(
        text: String
    ): NaturalLanguageIntent? {

        val patterns = listOf(
            Regex("^message (.+?) saying (.+)$"),
            Regex("^text (.+?) saying (.+)$"),
            Regex("^send a message to (.+?) saying (.+)$"),
            Regex("^send a text to (.+?) saying (.+)$")
        )

        for (pattern in patterns) {
            val match = pattern.matchEntire(text)
                ?: continue

            return NaturalLanguageIntent.TextMessage(
                contactName = match.groupValues[1].trim(),
                message = match.groupValues[2].trim()
            )
        }

        return null
    }

    private fun parseWhatsAppMessage(
        text: String
    ): NaturalLanguageIntent? {

        val patterns = listOf(
            Regex("^open whatsapp chat with (.+)$"),
            Regex("^open whatsapp message with (.+)$"),
            Regex("^message (.+) on whatsapp$"),
            Regex("^open whatsapp for (.+)$")
        )

        for (pattern in patterns) {
            val match = pattern.matchEntire(text)
                ?: continue

            return NaturalLanguageIntent.OpenWhatsAppMessage(
                match.groupValues[1].trim()
            )
        }

        return null
    }

    private fun parseAlarm(
        text: String
    ): NaturalLanguageIntent? {

        val patterns = listOf(
            Regex("^set an alarm for (.+)$"),
            Regex("^set an alarm at (.+)$"),
            Regex("^wake me up at (.+)$"),
            Regex("^set alarm at (.+)$")
        )

        for (pattern in patterns) {
            val match = pattern.matchEntire(text)
                ?: continue

            return NaturalLanguageIntent.SetAlarm(
                match.groupValues[1].trim()
            )
        }

        return null
    }

    private fun parseWake(
        text: String
    ): NaturalLanguageIntent? {

        val patterns = listOf(
            Regex("^wake me at (.+)$"),
            Regex("^wake me up at (.+)$"),
            Regex("^remind me to wake at (.+)$")
        )

        for (pattern in patterns) {
            val match = pattern.matchEntire(text)
                ?: continue

            return NaturalLanguageIntent.WakeAt(
                match.groupValues[1].trim()
            )
        }

        return null
    }

    private fun parseTimer(
        text: String
    ): NaturalLanguageIntent? {

        val patterns = listOf(
            Regex("^set a timer for (.+)$"),
            Regex("^start a timer for (.+)$"),
            Regex("^start timer for (.+)$"),
            Regex("^count down for (.+)$")
        )

        for (pattern in patterns) {
            val match = pattern.matchEntire(text)
                ?: continue

            return NaturalLanguageIntent.SetTimer(
                match.groupValues[1].trim()
            )
        }

        return null
    }

    private fun normalizeAppName(
        value: String
    ): String {

        return when (value.lowercase()) {
            "google chrome" -> "Chrome"
            "chrome browser" -> "Chrome"
            "whatsapp messenger" -> "WhatsApp"
            "youtube app" -> "YouTube"
            "camera app" -> "Camera"
            "settings app" -> "Settings"
            "calculator app" -> "Calculator"
            "phone app" -> "Phone"
            "messages app" -> "Messages"
            else -> value
        }
    }

    private fun containsAny(
        text: String,
        vararg phrases: String
    ): Boolean {

        return phrases.any { phrase ->
            text == phrase ||
                text.contains(
                    Regex.escape(phrase).toRegex()
                )
        }
    }

    private fun result(
        intent: NaturalLanguageIntent,
        confidence: Float
    ): NaturalLanguageResult {

        return NaturalLanguageResult(
            intent = intent,
            confidence = confidence,
            source = NaturalLanguageResult.Source.NATURAL_PATTERN
        )
    }
}
