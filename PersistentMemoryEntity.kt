package com.rider.persistent_memory.droidkaigi_gvp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rider_persistent_memory")
data class PersistentMemoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    val key: String,
    val value: String,
    val category: String,
    val createdAt: Long,
    val updatedAt: Long
)
