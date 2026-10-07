package com.rider.voice.model_ram_selection.droidkaigi_gvp

class RamBasedModelSelector {

    fun select(
        ramTier: RamTier,
        models: List<ModelRamRequirement>
    ): ModelRamRequirement? {

        if (models.isEmpty()) {
            return null
        }

        val compatible =
            models.filter { model ->

                when (ramTier) {

                    RamTier.LOW ->
                        model.modelSizeGb <= 1.5

                    RamTier.MEDIUM ->
                        model.modelSizeGb <= 3.0

                    RamTier.HIGH ->
                        model.modelSizeGb <= 5.0

                    RamTier.FLAGSHIP ->
                        model.modelSizeGb <= 8.0
                }
            }

        return compatible.maxByOrNull {
            it.modelSizeGb
        }
    }
}
