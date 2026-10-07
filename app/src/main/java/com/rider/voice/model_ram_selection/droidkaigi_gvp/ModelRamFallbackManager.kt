package com.rider.voice.model_ram_selection.droidkaigi_gvp

class ModelRamFallbackManager {

    fun findFallback(
        ramInfo: DeviceRamInfo,
        models: List<ModelRamRequirement>
    ): ModelRamRequirement? {

        return models
            .filter {
                ramInfo.availableRamGb >=
                    it.minimumRamGb
            }
            .sortedBy {
                it.modelSizeGb
            }
            .firstOrNull()
    }
}
