package com.rider.continuous_listening.droidkaigi_gvp

class Feature33AutoListenController(
    private val startListening: () -> Unit,
    private val stopListening: () -> Unit
) {

    private var enabled = true
    private var speaking = false
    private var listening = false
    private var waitingForTtsFinish = false

    /**
     * Enable or disable automatic listening.
     */
    fun setEnabled(value: Boolean) {
        enabled = value

        if (!enabled) {
            waitingForTtsFinish = false
        }
    }

    /**
     * Called when VISHU starts speaking.
     */
    fun onTtsStarted() {
        if (!enabled) return

        speaking = true
        waitingForTtsFinish = true

        // Do not listen while VISHU is speaking.
        if (listening) {
            stopListening()
            listening = false
        }
    }

    /**
     * Called when VISHU completely finishes speaking.
     *
     * Feature #33:
     * Automatically start listening again.
     */
    fun onTtsFinished() {
        if (!enabled) return

        speaking = false

        if (!waitingForTtsFinish) return

        waitingForTtsFinish = false
        startListening()
        listening = true
    }

    /**
     * Called when listening starts for any other reason.
     */
    fun onListeningStarted() {
        listening = true
    }

    /**
     * Called when listening stops.
     */
    fun onListeningStopped() {
        listening = false
    }

    /**
     * Called when the user starts speaking.
     *
     * User speech has priority over automatic state changes.
     */
    fun onUserSpeechStarted() {
        if (!enabled) return

        if (speaking) {
            speaking = false
            waitingForTtsFinish = false
        }
    }

    /**
     * Called when a new user command is received.
     *
     * Keeps automatic listening ready for the next turn.
     */
    fun onCommandReceived() {
        if (!enabled) return

        waitingForTtsFinish = false
    }

    /**
     * Manually stop the current listening state.
     */
    fun stop() {
        if (listening) {
            stopListening()
        }

        listening = false
        waitingForTtsFinish = false
    }

    /**
     * Reset the controller state.
     */
    fun reset() {
        speaking = false
        listening = false
        waitingForTtsFinish = false
    }

    fun isEnabled(): Boolean {
        return enabled
    }

    fun isSpeaking(): Boolean {
        return speaking
    }

    fun isListening(): Boolean {
        return listening
    }

    fun isWaitingForTtsFinish(): Boolean {
        return waitingForTtsFinish
    }
}
