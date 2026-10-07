package com.rider.voice.model_ram_selection.droidkaigi_gvp

import android.content.Context

class Feature73ModelRamSelection(
    context: Context
) {

    private val ramReader =
        AndroidRamReader(context)

    private val tierResolver =
        RamTierResolver()

    private val selector =
        RamBasedModelSelector()

    private val fallbackManager =
        ModelRamFallbackManager()

    private val compatibilityChecker =
        ModelRamCompatibilityChecker()

    fun getDeviceRam(): DeviceRamInfo =
        ramReader.read()

    fun getRamTier(): RamTier {

        val ram =
            ramReader.read()

        return tierResolver.resolve(
            ram.totalRamGb
        )
    }

    fun selectModel(
        models: List<ModelRamRequirement>
    ): ModelRamRequirement? {

        val ram =
            ramReader.read()

        val tier =
            tierResolver.resolve(
                ram.totalRamGb
            )

        val preferred =
            selector.select(
                tier,
                models
            )

        if (
            preferred != null &&
            compatibilityChecker.isCompatible(
                ram,
                preferred
            )
        ) {
            return preferred
        }

        return fallbackManager.findFallback(
            ram,
            models
        )
    }
}
