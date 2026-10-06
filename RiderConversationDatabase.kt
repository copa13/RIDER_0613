package com.rider.conversation_history.droidkaigi_gvp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        ConversationSessionEntity::class,
        ConversationMessageEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class RiderConversationDatabase : RoomDatabase() {

    abstract fun conversationDao(): ConversationDao

    companion object {

        @Volatile
        private var INSTANCE: RiderConversationDatabase? = null

        fun getInstance(
            context: Context
        ): RiderConversationDatabase {

            return INSTANCE ?: synchronized(this) {

                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    RiderConversationDatabase::class.java,
                    "rider_conversation_history.db"
                )
                    .build()
                    .also {
                        INSTANCE = it
                    }
            }
        }
    }
}
