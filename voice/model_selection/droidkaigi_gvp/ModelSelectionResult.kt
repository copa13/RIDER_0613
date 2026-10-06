package com.rider.model_selection.droidkaigi_gvp

sealed class ModelSelectionResult {

    data class Selected(
        val model: ModelInfo
    ) : ModelSelectionResult()

    data class AlreadySelected(
        val model: ModelInfo
    ) : ModelSelectionResult()

    data class Unavailable(
        val modelId: String,
        val reason: String
    ) : ModelSelectionResult()

    data class Incompatible(
        val modelId: String,
        val reason: String
    ) : ModelSelectionResult()

    data class NotFound(
        val modelId: String
    ) : ModelSelectionResult()

    data class Failed(
        val reason: String
    ) : ModelSelectionResult()
}
