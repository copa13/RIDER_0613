package com.rider.model_selection.droidkaigi_gvp

class ModelSelector(
    private val registry: ModelRegistry,
    private val compatibilityChecker: ModelCompatibilityChecker
) {

    private val activeModels =
        mutableMapOf<ModelType, ModelInfo>()

    fun select(modelId: String): ModelSelectionResult {

        val model = registry.find(modelId)
            ?: return ModelSelectionResult.NotFound(modelId)

        val compatibilityError =
            compatibilityChecker.check(model)

        if (compatibilityError != null) {

            return if (!model.installed) {
                ModelSelectionResult.Unavailable(
                    modelId = model.id,
                    reason = compatibilityError
                )
            } else {
                ModelSelectionResult.Incompatible(
                    modelId = model.id,
                    reason = compatibilityError
                )
            }
        }

        val current = activeModels[model.type]

        if (current?.id == model.id) {
            return ModelSelectionResult.AlreadySelected(model)
        }

        activeModels[model.type] = model

        return ModelSelectionResult.Selected(model)
    }

    fun getActive(type: ModelType): ModelInfo? {
        return activeModels[type]
    }

    fun clear(type: ModelType) {
        activeModels.remove(type)
    }

    fun clearAll() {
        activeModels.clear()
    }
}
