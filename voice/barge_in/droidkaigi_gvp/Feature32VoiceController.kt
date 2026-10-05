package voice.barage_in.droidkaigi_gvp

/**
 * RIDER Feature #32
 *
 * Barge-in controller:
 * User speaks while RIDER is speaking.
 *
 * Flow:
 * User speech detected
 *        ↓
 * Stop TTS
 *        ↓
 * Cancel old AI response
 *        ↓
 * Clear old response state
 *        ↓
 * Start listening again
 *        ↓
 * Send newest command to RIDER
 */
class Feature32VoiceController(
    private val stopTts: () -> Unit,
    private val cancelAiResponse: () -> Unit,
    private val clearOldResponse: () -> Unit,
    private val startListening: () -> Unit,
    private val onNewCommand: (String) -> Unit
) {

    private var isSpeaking = false
    private var isInterrupting = false

    fun setSpeaking(value: Boolean) {
        isSpeaking = value

        if (!value) {
            isInterrupting = false
        }
    }

    fun onUserSpeechStarted() {
        if (!isSpeaking || isInterrupting) {
            return
        }

        isInterrupting = true

        stopTts()
        cancelAiResponse()
        clearOldResponse()

        isSpeaking = false

        startListening()
    }

    fun onSpeechResult(text: String) {
        val command = text.trim()

        if (command.isEmpty()) {
            isInterrupting = false
            startListening()
            return
        }

        isInterrupting = false
        onNewCommand(command)
    }

    fun onResponseStarted() {
        isSpeaking = true
        isInterrupting = false
    }

    fun onResponseFinished() {
        isSpeaking = false
        isInterrupting = false
    }

    fun reset() {
        isSpeaking = false
        isInterrupting = false
    }
}
