package com.rider.voice.model_download.droidkaigi_gvp

import java.io.File
import java.io.RandomAccessFile
import java.net.HttpURLConnection
import java.net.URL

class ModelDownloadRepository {

    fun download(
        request: ModelDownloadRequest,
        targetFile: File,
        partialFile: File,
        onProgress: (
            Long,
            Long
        ) -> Unit
    ): Boolean {

        var existingBytes =
            if (partialFile.exists()) {
                partialFile.length()
            } else {
                0L
            }

        val connection =
            URL(request.downloadUrl)
                .openConnection() as HttpURLConnection

        try {
            if (existingBytes > 0L) {
                connection.setRequestProperty(
                    "Range",
                    "bytes=$existingBytes-"
                )
            }

            connection.connectTimeout = 20_000
            connection.readTimeout = 30_000
            connection.instanceFollowRedirects = true

            connection.connect()

            val responseCode =
                connection.responseCode

            val resumeAccepted =
                existingBytes > 0L &&
                responseCode ==
                    HttpURLConnection.HTTP_PARTIAL

            if (
                existingBytes > 0L &&
                !resumeAccepted
            ) {
                existingBytes = 0L
                partialFile.delete()
            }

            if (
                responseCode !=
                    HttpURLConnection.HTTP_OK &&
                responseCode !=
                    HttpURLConnection.HTTP_PARTIAL
            ) {
                return false
            }

            val contentLength =
                connection.getHeaderFieldLong(
                    "Content-Length",
                    -1L
                )

            val totalBytes =
                if (
                    resumeAccepted &&
                    contentLength >= 0L
                ) {
                    existingBytes + contentLength
                } else {
                    contentLength
                }

            connection.inputStream.use { input ->

                RandomAccessFile(
                    partialFile,
                    "rw"
                ).use { output ->

                    if (resumeAccepted) {
                        output.seek(existingBytes)
                    } else {
                        output.setLength(0L)
                    }

                    var downloaded =
                        existingBytes

                    val buffer =
                        ByteArray(64 * 1024)

                    while (true) {
                        val read =
                            input.read(buffer)

                        if (read == -1) break

                        output.write(
                            buffer,
                            0,
                            read
                        )

                        downloaded += read

                        onProgress(
                            downloaded,
                            totalBytes
                        )
                    }
                }
            }

            if (
                totalBytes >= 0L &&
                partialFile.length() != totalBytes
            ) {
                return false
            }

            if (targetFile.exists()) {
                targetFile.delete()
            }

            if (!partialFile.renameTo(targetFile)) {
                partialFile.copyTo(
                    targetFile,
                    overwrite = true
                )
                partialFile.delete()
            }

            return true

        } finally {
            connection.disconnect()
        }
    }
}
