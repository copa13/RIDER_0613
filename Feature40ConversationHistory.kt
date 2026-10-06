package com.rider.conversation_history.droidkaigi_gvp

class Feature40ConversationHistory(
    private val dao: ConversationDao
) {

    private var activeSessionId: String? = null

    suspend fun createSession(): String {

        val now = System.currentTimeMillis()
        val sessionId = "session_$now"

        dao.insertSession(
            ConversationSessionEntity(
                id = sessionId,
                createdAt = now,
                updatedAt = now
            )
        )

        activeSessionId = sessionId

        return sessionId
    }

    suspend fun selectSession(
        sessionId: String
    ): Boolean {

        val session = dao.getSession(sessionId)

        if (session == null) {
            return false
        }

        activeSessionId = sessionId

        return true
    }

    suspend fun addUserMessage(
        text: String
    ): Boolean {

        return addMessage(
            role = "user",
            text = text
        )
    }

    suspend fun addAssistantMessage(
        text: String
    ): Boolean {

        return addMessage(
            role = "assistant",
            text = text
        )
    }

    private suspend fun addMessage(
        role: String,
        text: String
    ): Boolean {

        val sessionId =
            activeSessionId ?: return false

        val cleanText = text.trim()

        if (cleanText.isEmpty()) {
            return false
        }

        val now = System.currentTimeMillis()

        dao.insertMessage(
            ConversationMessageEntity(
                sessionId = sessionId,
                role = role,
                text = cleanText,
                timestamp = now
            )
        )

        dao.updateSessionTime(
            sessionId = sessionId,
            updatedAt = now
        )

        return true
    }

    suspend fun getActiveMessages():
        List<ConversationMessageEntity> {

        val sessionId =
            activeSessionId ?: return emptyList()

        return dao.getMessages(sessionId)
    }

    suspend fun getMessages(
        sessionId: String
    ): List<ConversationMessageEntity> {

        return dao.getMessages(sessionId)
    }

    suspend fun getAllSessions():
        List<ConversationSessionEntity> {

        return dao.getAllSessions()
    }

    suspend fun search(
        query: String
    ): List<ConversationMessageEntity> {

        val cleanQuery = query.trim()

        if (cleanQuery.isEmpty()) {
            return emptyList()
        }

        return dao.searchMessages(cleanQuery)
    }

    suspend fun deleteSession(
        sessionId: String
    ) {

        dao.deleteMessages(sessionId)
        dao.deleteSession(sessionId)

        if (activeSessionId == sessionId) {
            activeSessionId = null
        }
    }

    suspend fun clearAllHistory() {

        dao.clearAllMessages()
        dao.clearAllSessions()

        activeSessionId = null
    }

    fun getActiveSessionId(): String? {
        return activeSessionId
    }

    fun resetActiveSession() {
        activeSessionId = null
    }
}
