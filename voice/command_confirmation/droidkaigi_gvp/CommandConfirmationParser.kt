package com.rider.command_confirmation.droidkaigi_gvp

class CommandConfirmationParser {

    fun parse(text: String): ConfirmationDecision {
        val value = text
            .trim()
            .lowercase()
            .replace(Regex("[^a-z0-9 ]"), " ")
            .replace(Regex("\\s+"), " ")

        return when {
            value == "yes" ||
            value == "yeah" ||
            value == "yep" ||
            value == "confirm" ||
            value == "confirmed" ||
            value == "do it" ||
            value == "go ahead" ||
            value == "okay" ||
            value == "ok" -> ConfirmationDecision.YES

            value == "no" ||
            value == "nope" ||
            value == "cancel" ||
            value == "cancel it" ||
            value == "don't" ||
            value == "do not" ||
            value == "stop" -> ConfirmationDecision.NO

            else -> ConfirmationDecision.UNKNOWN
        }
    }
}
