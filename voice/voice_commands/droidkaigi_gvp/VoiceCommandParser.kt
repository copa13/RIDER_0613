package com.rider.voice_commands.droidkaigi_gvp

class VoiceCommandParser {

    fun parse(input: String): VoiceCommand? {

        val text = input
            .trim()
            .lowercase()

        if (text.isEmpty()) return null

        when {
            text == "volume up" ||
            text == "increase volume" ||
            text == "louder" -> {
                return VoiceCommand.VolumeUp
            }

            text == "volume down" ||
            text == "decrease volume" ||
            text == "quieter" -> {
                return VoiceCommand.VolumeDown
            }

            text == "mute" ||
            text == "mute volume" -> {
                return VoiceCommand.Mute
            }

            text == "flashlight on" ||
            text == "turn on flashlight" -> {
                return VoiceCommand.FlashlightOn
            }

            text == "flashlight off" ||
            text == "turn off flashlight" -> {
                return VoiceCommand.FlashlightOff
            }

            text == "lock" ||
            text == "lock phone" ||
            text == "lock screen" -> {
                return VoiceCommand.LockPhone
            }

            text == "open settings" ||
            text == "settings" -> {
                return VoiceCommand.OpenSettings
            }

            text == "open power menu" ||
            text == "power menu" -> {
                return VoiceCommand.OpenPowerMenu
            }

            text == "go back" ||
            text == "back" -> {
                return VoiceCommand.GoBack
            }

            text == "go home" ||
            text == "home" -> {
                return VoiceCommand.GoHome
            }

            text == "open recents" ||
            text == "recent apps" -> {
                return VoiceCommand.OpenRecents
            }

            text == "open notifications" ||
            text == "notifications" -> {
                return VoiceCommand.OpenNotifications
            }

            text == "open quick settings" ||
            text == "quick settings" -> {
                return VoiceCommand.OpenQuickSettings
            }

            text == "stop" ||
            text == "cancel" -> {
                return VoiceCommand.Stop
            }

            text.startsWith("open ") -> {
                val appName = text.removePrefix("open ").trim()

                if (appName.isNotEmpty()) {
                    return VoiceCommand.OpenApp(appName)
                }
            }

            text.startsWith("call ") -> {
                val contact = text.removePrefix("call ").trim()

                if (contact.isNotEmpty()) {
                    return VoiceCommand.Call(contact)
                }
            }

            text.startsWith("message ") -> {
                return parseMessage(text)
            }
        }

        return null
    }

    private fun parseMessage(text: String): VoiceCommand? {

        val remaining = text.removePrefix("message ").trim()

        val separator = remaining.indexOf(" saying ")

        if (separator <= 0) return null

        val contact = remaining.substring(
            0,
            separator
        ).trim()

        val message = remaining.substring(
            separator + " saying ".length
        ).trim()

        if (contact.isEmpty() || message.isEmpty()) {
            return null
        }

        return VoiceCommand.MessageDraft(
            contactName = contact,
            message = message
        )
    }
}
