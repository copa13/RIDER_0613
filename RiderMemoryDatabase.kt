package com.rider.persistent_memory.droidkaigi_gvp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [PersistentMemoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class RiderMemoryDatabase : RoomDatabase() {

    abstract fun memoryDao(): PersistentMemoryDao

    companion object {

        @Volatile
        private var INSTANCE: RiderMemoryDatabase? = null

        fun getInstance(
            context: Context
        ): RiderMemoryDatabase {

            return INSTANCE ?: synchronized(this) {

                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    RiderMemoryDatabase::class.java,
                    "rider_persistent_memory.db"
                )
                    .build()
                    .also {
                        INSTANCE = it
                    }
            }
        }
    }
}
