package com.rider.natural_language.droidkaigi_gvp

class NaturalLanguageValidator(
    private val minimumConfidence: Float = 0.80f
) {

    fun validate(
        result: NaturalLanguageResult
    ): Boolean {

        if (result.confidence < minimumConfidence) {
            return false
        }

        return when (val intent = result.intent) {

            is NaturalLanguageIntent.OpenApp ->
                intent.appName.isNotBlank()

            is NaturalLanguageIntent.Call ->
                intent.contactName.isNotBlank()

            is NaturalLanguageIntent.DraftMessage ->
                intent.contactName.isNotBlank() &&
                    intent.message.isNotBlank()

            is NaturalLanguageIntent.TextMessage ->
                intent.contactName.isNotBlank() &&
                    intent.message.isNotBlank()

            is NaturalLanguageIntent.OpenWhatsAppMessage ->
                intent.contactName.isNotBlank()

            is NaturalLanguageIntent.SetAlarm ->
                intent.timeText.isNotBlank()

            is NaturalLanguageIntent.WakeAt ->
                intent.timeText.isNotBlank()

            is NaturalLanguageIntent.SetTimer ->
                intent.durationText.isNotBlank()

            else -> true
        }
    }
}
