package com.rider.voice.model_ram_selection.droidkaigi_gvp

class RamTierResolver {

    fun resolve(
        totalRamGb: Double
    ): RamTier {

        return when {
            totalRamGb < 4.0 ->
                RamTier.LOW

            totalRamGb < 8.0 ->
                RamTier.MEDIUM

            totalRamGb < 12.0 ->
                RamTier.HIGH

            else ->
                RamTier.FLAGSHIP
        }
    }
}
