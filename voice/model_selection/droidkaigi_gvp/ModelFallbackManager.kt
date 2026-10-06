package com.rider.model_selection.droidkaigi_gvp

class ModelFallbackManager(
    private val registry: ModelRegistry,
    private val selector: ModelSelector
) {

    fun selectFallback(
        type: ModelType,
        failedModelId: String
    ): ModelSelectionResult {

        val candidates = registry
            .getInstalled(type)
            .filter {
                it.id != failedModelId
            }

        if (candidates.isEmpty()) {
            return ModelSelectionResult.Failed(
                reason = "No compatible fallback model is available."
            )
        }

        return selector.select(candidates.first().id)
    }
}
