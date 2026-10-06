package com.rider.voice.model_download.droidkaigi_gvp

sealed class ModelDownloadStatus {
    data object Idle : ModelDownloadStatus()

    data class Downloading(
        val modelId: String,
        val downloadedBytes: Long,
        val totalBytes: Long
    ) : ModelDownloadStatus()

    data class Paused(
        val modelId: String,
        val downloadedBytes: Long,
        val totalBytes: Long
    ) : ModelDownloadStatus()

    data class Verifying(
        val modelId: String
    ) : ModelDownloadStatus()

    data class Ready(
        val modelId: String,
        val localPath: String
    ) : ModelDownloadStatus()

    data class Resumed(
        val modelId: String,
        val downloadedBytes: Long,
        val totalBytes: Long
    ) : ModelDownloadStatus()

    data class Failed(
        val modelId: String,
        val message: String
    ) : ModelDownloadStatus()
}
