package com.rider.voice.offline_operation.droidkaigi_gvp

class Feature80Readiness(
    private val feature: Feature80OfflineOperation
) {

    fun check(): OfflineReadinessReport {
        val status = feature.getStatus()

        return OfflineReadinessReport(
            offlineModeEnabled = status.offlineModeEnabled,
            allRequiredCapabilitiesReady = status.ready,
            missingCapabilities = status.missingCapabilities,
            componentCount = status.components.size,
            readyComponentCount = status.components.count {
                it.ready
            }
        )
    }
}
