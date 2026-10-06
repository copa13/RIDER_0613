package com.rider.voice.model_download.droidkaigi_gvp

import android.content.Context
import java.io.File

class ModelCacheManager(
    context: Context
) {
    private val cacheDirectory =
        File(context.filesDir, "rider_models")

    init {
        if (!cacheDirectory.exists()) {
            cacheDirectory.mkdirs()
        }
    }

    fun getModelFile(
        modelId: String,
        fileName: String
    ): File {
        val directory =
            File(cacheDirectory, safeId(modelId))

        if (!directory.exists()) {
            directory.mkdirs()
        }

        return File(directory, fileName)
    }

    fun getPartialFile(
        modelId: String,
        fileName: String
    ): File {
        val directory =
            File(cacheDirectory, safeId(modelId))

        if (!directory.exists()) {
            directory.mkdirs()
        }

        return File(directory, "$fileName.part")
    }

    fun deleteModel(
        modelId: String,
        fileName: String
    ) {
        getModelFile(modelId, fileName).delete()
        getPartialFile(modelId, fileName).delete()
    }

    private fun safeId(value: String): String =
        value.replace(
            Regex("[^a-zA-Z0-9._-]"),
            "_"
        )
}
