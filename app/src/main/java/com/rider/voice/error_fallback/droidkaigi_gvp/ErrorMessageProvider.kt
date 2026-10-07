package com.rider.voice.error_fallback.droidkaigi_gvp

object ErrorMessageProvider {

    fun messageFor(error: RiderError): String {
        return when (error.type) {

            ErrorType.VAD ->
                "I could not detect your speech correctly."

            ErrorType.STT ->
                "I could not understand what you said."

            ErrorType.TTS ->
                "I could not produce the voice response."

            ErrorType.MODEL ->
                "The required AI model is not available."

            ErrorType.DOWNLOAD ->
                "The required model could not be downloaded."

            ErrorType.COMMAND ->
                "I could not understand that command."

            ErrorType.ACTION ->
                "I understood the request, but I could not complete the action."

            ErrorType.TIMEOUT ->
                "The operation took too long."

            ErrorType.PERMISSION ->
                "The required permission is not available."

            ErrorType.UNKNOWN ->
                "Something went wrong."
        }
    }
}
