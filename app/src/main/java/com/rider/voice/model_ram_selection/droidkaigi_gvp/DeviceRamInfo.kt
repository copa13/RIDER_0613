package com.rider.voice.model_ram_selection.droidkaigi_gvp

data class DeviceRamInfo(
    val totalRamBytes: Long,
    val availableRamBytes: Long
) {
    val totalRamGb: Double
        get() = totalRamBytes / 1_073_741_824.0

    val availableRamGb: Double
        get() = availableRamBytes / 1_073_741_824.0
}
