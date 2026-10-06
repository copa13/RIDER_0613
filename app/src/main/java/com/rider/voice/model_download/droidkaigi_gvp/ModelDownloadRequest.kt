package com.rider.voice.model_download.droidkaigi_gvp

data class ModelDownloadRequest(
    val modelId: String,
    val downloadUrl: String,
    val expectedSha256: String? = null,
    val expectedSizeBytes: Long? = null,
    val fileName: String
)
