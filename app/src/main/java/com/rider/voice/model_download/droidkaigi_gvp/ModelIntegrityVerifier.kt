package com.rider.voice.model_download.droidkaigi_gvp

import java.io.File
import java.io.FileInputStream
import java.security.MessageDigest

class ModelIntegrityVerifier {

    fun verify(
        file: File,
        expectedSha256: String?,
        expectedSizeBytes: Long?
    ): Boolean {

        if (!file.exists() || !file.isFile) {
            return false
        }

        if (
            expectedSizeBytes != null &&
            file.length() != expectedSizeBytes
        ) {
            return false
        }

        if (!expectedSha256.isNullOrBlank()) {
            val actualSha256 = sha256(file)

            if (
                !actualSha256.equals(
                    expectedSha256,
                    ignoreCase = true
                )
            ) {
                return false
            }
        }

        return true
    }

    private fun sha256(file: File): String {
        val digest =
            MessageDigest.getInstance("SHA-256")

        FileInputStream(file).use { input ->
            val buffer = ByteArray(64 * 1024)

            while (true) {
                val read = input.read(buffer)

                if (read <= 0) break

                digest.update(buffer, 0, read)
            }
        }

        return digest.digest()
            .joinToString("") {
                "%02x".format(it)
            }
    }
}
