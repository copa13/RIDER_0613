package com.rider.natural_language.droidkaigi_gvp

data class NaturalLanguageResult(
    val intent: NaturalLanguageIntent,
    val confidence: Float,
    val source: Source
) {
    enum class Source {
        EXACT_COMMAND,
        RULE,
        NATURAL_PATTERN
    }
}
