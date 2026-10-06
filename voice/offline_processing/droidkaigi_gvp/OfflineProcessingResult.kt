package com.rider.offline_processing.droidkaigi_gvp

sealed class OfflineProcessingResult {

    data class Ready(
        val offline: Boolean
    ) : OfflineProcessingResult()

    data class Processed(
        val offline: Boolean
    ) : OfflineProcessingResult()

    data class Blocked(
        val reason: String
    ) : OfflineProcessingResult()

    data class Failed(
        val reason: String
    ) : OfflineProcessingResult()
}
