package com.rider.voice.offline_operation.droidkaigi_gvp

data class OfflineReadinessReport(
    val offlineModeEnabled: Boolean,
    val allRequiredCapabilitiesReady: Boolean,
    val missingCapabilities: Set<OfflineCapability>,
    val componentCount: Int,
    val readyComponentCount: Int
)
