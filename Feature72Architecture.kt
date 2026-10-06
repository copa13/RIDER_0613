package com.rider.model_download.droidkaigi_gvp

object Feature72Architecture {

    const val FEATURE_NUMBER = 72

    const val FEATURE_NAME =
        "Model Download / Cache"

    const val ONLINE_DOWNLOAD = true

    const val OFFLINE_CACHE_USAGE = true

    const val RESUMABLE_DOWNLOAD = true

    const val SHA256_VERIFICATION = true

    const val SIZE_VERIFICATION = true

    const val CORRUPT_CACHE_DETECTION = true

    const val PARTIAL_DOWNLOAD_SUPPORT = true

    const val SAFE_CACHE_REPLACEMENT = true

    const val USES_FEATURE_71_SELECTION = true

    const val CREATES_NEW_MODEL_SELECTION = false

    const val CREATES_NEW_COMMANDS = false

    const val OFFLINE_INFERENCE =
        "Uses an already verified local model without requiring network access."
}
