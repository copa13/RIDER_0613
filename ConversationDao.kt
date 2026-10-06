package com.rider.conversation_history.droidkaigi_gvp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ConversationDao {

    @Insert
    suspend fun insertSession(
        session: ConversationSessionEntity
    )

    @Insert
    suspend fun insertMessage(
        message: ConversationMessageEntity
    )

    @Query("""
        SELECT * FROM conversation_sessions
        ORDER BY updatedAt DESC
    """)
    suspend fun getAllSessions(): List<ConversationSessionEntity>

    @Query("""
        SELECT * FROM conversation_sessions
        WHERE id = :sessionId
        LIMIT 1
    """)
    suspend fun getSession(
        sessionId: String
    ): ConversationSessionEntity?

    @Query("""
        SELECT * FROM conversation_messages
        WHERE sessionId = :sessionId
        ORDER BY timestamp ASC, id ASC
    """)
    suspend fun getMessages(
        sessionId: String
    ): List<ConversationMessageEntity>

    @Query("""
        SELECT * FROM conversation_messages
        WHERE text LIKE '%' || :query || '%'
        ORDER BY timestamp DESC
    """)
    suspend fun searchMessages(
        query: String
    ): List<ConversationMessageEntity>

    @Query("""
        UPDATE conversation_sessions
        SET updatedAt = :updatedAt
        WHERE id = :sessionId
    """)
    suspend fun updateSessionTime(
        sessionId: String,
        updatedAt: Long
    )

    @Query("""
        DELETE FROM conversation_messages
        WHERE sessionId = :sessionId
    """)
    suspend fun deleteMessages(
        sessionId: String
    )

    @Query("""
        DELETE FROM conversation_sessions
        WHERE id = :sessionId
    """)
    suspend fun deleteSession(
        sessionId: String
    )

    @Query("DELETE FROM conversation_messages")
    suspend fun clearAllMessages()

    @Query("DELETE FROM conversation_sessions")
    suspend fun clearAllSessions()
}
