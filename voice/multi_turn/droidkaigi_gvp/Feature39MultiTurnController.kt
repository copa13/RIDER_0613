package com.rider.multi_turn.droidkaigi_gvp

data class ConversationTurn(
    val userText: String,
    val assistantText: String
)

class Feature39MultiTurnController(
    private val maxContextTurns: Int = 0
) {

    private val turns = mutableListOf<ConversationTurn>()

    private var currentUserText: String? = null
    private var active = true

    /**
     * Add the user's latest message.
     */
    fun onUserMessage(text: String) {
        if (!active) return

        val cleanText = text.trim()

        if (cleanText.isEmpty()) return

        currentUserText = cleanText
    }

    /**
     * Add RIDER's response and complete the current turn.
     */
    fun onAssistantResponse(text: String) {
        if (!active) return

        val userText = currentUserText ?: return
        val assistantText = text.trim()

        if (assistantText.isEmpty()) return

        turns.add(
            ConversationTurn(
                userText = userText,
                assistantText = assistantText
            )
        )

        currentUserText = null

        trimContextIfNeeded()
    }

    /**
     * Returns the previous conversation context.
     *
     * maxContextTurns = 0 means no artificial turn limit.
     */
    fun getContext(): List<ConversationTurn> {
        return turns.toList()
    }

    /**
     * Builds a readable context for an AI/LLM backend.
     */
    fun buildContextPrompt(): String {
        if (turns.isEmpty()) {
            return ""
        }

        val builder = StringBuilder()

        for (turn in turns) {
            builder.append("User: ")
                .append(turn.userText)
                .append('\n')

            builder.append("RIDER: ")
                .append(turn.assistantText)
                .append('\n')
        }

        return builder.toString().trim()
    }

    /**
     * Returns the latest completed conversation turn.
     */
    fun getLastTurn(): ConversationTurn? {
        return turns.lastOrNull()
    }

    /**
     * Number of completed turns currently available.
     */
    fun turnCount(): Int {
        return turns.size
    }

    /**
     * Checks whether there is previous conversation context.
     */
    fun hasContext(): Boolean {
        return turns.isNotEmpty()
    }

    /**
     * Clears only the current in-memory conversation.
     *
     * This does NOT delete persistent history because
     * persistent storage belongs to Feature #40/#41.
     */
    fun clearCurrentConversation() {
        turns.clear()
        currentUserText = null
    }

    /**
     * Enable/disable multi-turn processing.
     */
    fun setEnabled(value: Boolean) {
        active = value

        if (!active) {
            currentUserText = null
        }
    }

    fun isEnabled(): Boolean {
        return active
    }

    /**
     * Prevents an artificial limit when maxContextTurns == 0.
     */
    private fun trimContextIfNeeded() {
        if (maxContextTurns <= 0) return

        while (turns.size > maxContextTurns) {
            turns.removeAt(0)
        }
    }

    fun reset() {
        turns.clear()
        currentUserText = null
        active = true
    }
}
