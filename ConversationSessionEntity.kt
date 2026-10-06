package com.rider.conversation_history.droidkaigi_gvp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "conversation_sessions")
data class ConversationSessionEntity(
    @PrimaryKey
    val id: String,

    val createdAt: Long,

    val updatedAt: Long
)
