package com.rider.model_selection.droidkaigi_gvp

data class ModelInfo(
    val id: String,
    val name: String,
    val type: ModelType,
    val runtime: ModelRuntime,
    val local: Boolean = true,
    val installed: Boolean = false,
    val enabled: Boolean = true
)
