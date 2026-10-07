package com.rider.voice.model_ram_selection.droidkaigi_gvp

class ModelRamCompatibilityChecker {

    fun isCompatible(
        ramInfo: DeviceRamInfo,
        requirement: ModelRamRequirement
    ): Boolean {

        return ramInfo.availableRamGb >=
            requirement.minimumRamGb
    }
}
