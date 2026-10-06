package com.rider.voice.model_download.droidkaigi_gvp

import android.content.Context

class Feature72ModelDownload(
    context: Context
) {
    private val manager =
        ModelDownloadManager(context)

    fun downloadOrUseCache(
        request: ModelDownloadRequest,
        onStatusChanged:
            (ModelDownloadStatus) -> Unit = {}
    ): ModelDownloadResult =
        manager.downloadOrUseCache(
            request,
            onStatusChanged
        )

    fun verifyCache(
        request: ModelDownloadRequest
    ): Boolean =
        manager.verifyCachedModel(request)

    fun deleteCache(
        request: ModelDownloadRequest
    ) =
        manager.deleteCachedModel(request)
}
