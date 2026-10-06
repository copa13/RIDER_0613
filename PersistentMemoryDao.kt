package com.rider.persistent_memory.droidkaigi_gvp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PersistentMemoryDao {

    @Insert
    suspend fun insert(
        memory: PersistentMemoryEntity
    ): Long

    @Query("""
        SELECT * FROM rider_persistent_memory
        ORDER BY updatedAt DESC
    """)
    suspend fun getAll(): List<PersistentMemoryEntity>

    @Query("""
        SELECT * FROM rider_persistent_memory
        WHERE key = :key
        LIMIT 1
    """)
    suspend fun findByKey(
        key: String
    ): PersistentMemoryEntity?

    @Query("""
        SELECT * FROM rider_persistent_memory
        WHERE value LIKE '%' || :query || '%'
           OR key LIKE '%' || :query || '%'
           OR category LIKE '%' || :query || '%'
        ORDER BY updatedAt DESC
    """)
    suspend fun search(
        query: String
    ): List<PersistentMemoryEntity>

    @Query("""
        UPDATE rider_persistent_memory
        SET value = :value,
            category = :category,
            updatedAt = :updatedAt
        WHERE key = :key
    """)
    suspend fun update(
        key: String,
        value: String,
        category: String,
        updatedAt: Long
    )

    @Query("""
        DELETE FROM rider_persistent_memory
        WHERE id = :id
    """)
    suspend fun delete(
        id: Long
    )

    @Query("""
        DELETE FROM rider_persistent_memory
        WHERE key = :key
    """)
    suspend fun deleteByKey(
        key: String
    )

    @Query("""
        DELETE FROM rider_persistent_memory
    """)
    suspend fun clearAll()
}
