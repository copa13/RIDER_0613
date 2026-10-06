package com.rider.voice_commands.droidkaigi_gvp

import java.util.Locale

object TimeParser {

    fun parseAlarmHourMinute(text: String): Pair<Int, Int>? {
        val clean = text
            .trim()
            .lowercase(Locale.US)

        Regex(
            "^(\\d{1,2})(?::(\\d{1,2}))?\\s*(am|pm)?$"
        ).matchEntire(clean)?.let { match ->

            var hour = match.groupValues[1].toInt()
            val minute =
                match.groupValues[2]
                    .ifEmpty { "0" }
                    .toInt()

            val meridiem = match.groupValues[3]

            if (minute !in 0..59) return null

            if (meridiem == "am") {
                if (hour == 12) hour = 0
            } else if (meridiem == "pm") {
                if (hour != 12) hour += 12
            }

            if (hour !in 0..23) return null

            return hour to minute
        }

        return null
    }

    fun parseDurationMillis(text: String): Long? {
        val clean = text
            .trim()
            .lowercase(Locale.US)

        var total = 0L

        Regex("(\\d+)\\s*hour").find(clean)?.let {
            total += it.groupValues[1].toLong() * 60L * 60L * 1000L
        }

        Regex("(\\d+)\\s*minute").find(clean)?.let {
            total += it.groupValues[1].toLong() * 60L * 1000L
        }

        Regex("(\\d+)\\s*second").find(clean)?.let {
            total += it.groupValues[1].toLong() * 1000L
        }

        return if (total > 0L) total else null
    }
}
