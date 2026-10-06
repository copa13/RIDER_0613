package com.rider.conversation_history.droidkaigi_gvp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "conversation_messages")
data class ConversationMessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    val sessionId: String,

    val role: String,

    val text: String,

    val timestamp: Long
)
