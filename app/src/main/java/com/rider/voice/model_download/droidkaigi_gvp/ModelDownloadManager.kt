package com.rider.voice.model_download.droidkaigi_gvp

import android.content.Context

class ModelDownloadManager(
    context: Context
) {
    private val cache =
        ModelCacheManager(context)

    private val repository =
        ModelDownloadRepository()

    private val verifier =
        ModelIntegrityVerifier()

    fun downloadOrUseCache(
        request: ModelDownloadRequest,
        onStatusChanged:
            (ModelDownloadStatus) -> Unit = {}
    ): ModelDownloadResult {

        val modelFile =
            cache.getModelFile(
                request.modelId,
                request.fileName
            )

        val partialFile =
            cache.getPartialFile(
                request.modelId,
                request.fileName
            )

        if (modelFile.exists()) {

            onStatusChanged(
                ModelDownloadStatus.Verifying(
                    request.modelId
                )
            )

            if (
                verifier.verify(
                    modelFile,
                    request.expectedSha256,
                    request.expectedSizeBytes
                )
            ) {
                onStatusChanged(
                    ModelDownloadStatus.Ready(
                        request.modelId,
                        modelFile.absolutePath
                    )
                )

                return ModelDownloadResult.AlreadyCached(
                    request.modelId,
                    modelFile.absolutePath
                )
            }

            cache.deleteModel(
                request.modelId,
                request.fileName
            )
        }

        val hadPartial =
            partialFile.exists() &&
            partialFile.length() > 0L

        if (hadPartial) {
            onStatusChanged(
                ModelDownloadStatus.Resumed(
                    request.modelId,
                    partialFile.length(),
                    request.expectedSizeBytes ?: -1L
                )
            )
        }

        val success =
            try {
                repository.download(
                    request,
                    modelFile,
                    partialFile
                ) { downloaded, total ->

                    onStatusChanged(
                        ModelDownloadStatus.Downloading(
                            request.modelId,
                            downloaded,
                            total
                        )
                    )
                }
            } catch (error: Exception) {

                onStatusChanged(
                    ModelDownloadStatus.Failed(
                        request.modelId,
                        error.message
                            ?: "Download failed."
                    )
                )

                return ModelDownloadResult.Failed(
                    request.modelId,
                    error.message
                        ?: "Download failed."
                )
            }

        if (!success) {
            return ModelDownloadResult.Failed(
                request.modelId,
                "Download did not complete."
            )
        }

        onStatusChanged(
            ModelDownloadStatus.Verifying(
                request.modelId
            )
        )

        if (
            !verifier.verify(
                modelFile,
                request.expectedSha256,
                request.expectedSizeBytes
            )
        ) {
            modelFile.delete()

            return ModelDownloadResult.Failed(
                request.modelId,
                "Model verification failed."
            )
        }

        onStatusChanged(
            ModelDownloadStatus.Ready(
                request.modelId,
                modelFile.absolutePath
            )
        )

        return if (hadPartial) {
            ModelDownloadResult.Resumed(
                request.modelId,
                modelFile.absolutePath
            )
        } else {
            ModelDownloadResult.Downloaded(
                request.modelId,
                modelFile.absolutePath
            )
        }
    }

    fun verifyCachedModel(
        request: ModelDownloadRequest
    ): Boolean {

        val file =
            cache.getModelFile(
                request.modelId,
                request.fileName
            )

        return verifier.verify(
            file,
            request.expectedSha256,
            request.expectedSizeBytes
        )
    }

    fun deleteCachedModel(
        request: ModelDownloadRequest
    ) {
        cache.deleteModel(
            request.modelId,
            request.fileName
        )
    }
}
