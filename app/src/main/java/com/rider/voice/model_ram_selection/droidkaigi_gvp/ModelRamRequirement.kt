package com.rider.voice.model_ram_selection.droidkaigi_gvp

data class ModelRamRequirement(
    val modelId: String,
    val minimumRamGb: Double,
    val recommendedRamGb: Double,
    val modelSizeGb: Double,
    val contextSize: Int
)
