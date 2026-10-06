package com.rider.voice.model_download.droidkaigi_gvp

sealed class ModelDownloadResult {
    data class Downloaded(
        val modelId: String,
        val localPath: String
    ) : ModelDownloadResult()

    data class AlreadyCached(
        val modelId: String,
        val localPath: String
    ) : ModelDownloadResult()

    data class Resumed(
        val modelId: String,
        val localPath: String
    ) : ModelDownloadResult()

    data class Failed(
        val modelId: String,
        val message: String
    ) : ModelDownloadResult()

    data class InvalidCache(
        val modelId: String,
        val message: String
    ) : ModelDownloadResult()
}
