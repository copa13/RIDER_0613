package com.rider.natural_language.droidkaigi_gvp

class NaturalLanguageNormalizer {

    fun normalize(input: String): String {
        return input
            .trim()
            .lowercase()
            .replace(Regex("[!?.,]+"), " ")
            .replace(Regex("\\s+"), " ")
            .trim()
    }

    fun removePoliteness(text: String): String {
        var result = text

        val words = listOf(
            "please",
            "can you",
            "could you",
            "would you",
            "will you",
            "i want you to",
            "i need you to",
            "would you please"
        )

        for (word in words) {
            result = result.replace(
                Regex("\\b${Regex.escape(word)}\\b"),
                " "
            )
        }

        return result
            .replace(Regex("\\s+"), " ")
            .trim()
    }
}
