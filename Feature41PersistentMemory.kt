package com.rider.persistent_memory.droidkaigi_gvp

class Feature41PersistentMemory(
    private val dao: PersistentMemoryDao
) {

    suspend fun remember(
        key: String,
        value: String,
        category: String = "general"
    ): Boolean {

        val cleanKey = key.trim()
        val cleanValue = value.trim()
        val cleanCategory = category.trim()

        if (cleanKey.isEmpty() || cleanValue.isEmpty()) {
            return false
        }

        val now = System.currentTimeMillis()

        val existing = dao.findByKey(cleanKey)

        if (existing == null) {

            dao.insert(
                PersistentMemoryEntity(
                    key = cleanKey,
                    value = cleanValue,
                    category =
                        if (cleanCategory.isEmpty()) {
                            "general"
                        } else {
                            cleanCategory
                        },
                    createdAt = now,
                    updatedAt = now
                )
            )

        } else {

            dao.update(
                key = cleanKey,
                value = cleanValue,
                category =
                    if (cleanCategory.isEmpty()) {
                        existing.category
                    } else {
                        cleanCategory
                    },
                updatedAt = now
            )
        }

        return true
    }

    suspend fun recall(
        key: String
    ): PersistentMemoryEntity? {

        val cleanKey = key.trim()

        if (cleanKey.isEmpty()) {
            return null
        }

        return dao.findByKey(cleanKey)
    }

    suspend fun search(
        query: String
    ): List<PersistentMemoryEntity> {

        val cleanQuery = query.trim()

        if (cleanQuery.isEmpty()) {
            return emptyList()
        }

        return dao.search(cleanQuery)
    }

    suspend fun getAllMemories():
        List<PersistentMemoryEntity> {

        return dao.getAll()
    }

    suspend fun forget(
        key: String
    ) {

        val cleanKey = key.trim()

        if (cleanKey.isEmpty()) {
            return
        }

        dao.deleteByKey(cleanKey)
    }

    suspend fun forgetById(
        id: Long
    ) {

        dao.delete(id)
    }

    suspend fun clearAllMemories() {
        dao.clearAll()
    }

    suspend fun hasMemory(
        key: String
    ): Boolean {

        return dao.findByKey(key.trim()) != null
    }

    suspend fun memoryCount(): Int {
        return dao.getAll().size
    }
}
