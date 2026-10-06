package com.rider.model_selection.droidkaigi_gvp

class Feature71ModelSelection {

    private val registry = ModelRegistry()

    private val compatibilityChecker =
        ModelCompatibilityChecker()

    private val selector =
        ModelSelector(
            registry = registry,
            compatibilityChecker = compatibilityChecker
        )

    private val fallbackManager =
        ModelFallbackManager(
            registry = registry,
            selector = selector
        )

    fun registerModel(model: ModelInfo) {
        registry.register(model)
    }

    fun removeModel(modelId: String) {
        registry.remove(modelId)
    }

    fun getModels(): List<ModelInfo> {
        return registry.getAll()
    }

    fun getModels(type: ModelType): List<ModelInfo> {
        return registry.getByType(type)
    }

    fun selectModel(modelId: String): ModelSelectionResult {
        return selector.select(modelId)
    }

    fun getActiveModel(type: ModelType): ModelInfo? {
        return selector.getActive(type)
    }

    fun selectFallback(
        type: ModelType,
        failedModelId: String
    ): ModelSelectionResult {
        return fallbackManager.selectFallback(
            type = type,
            failedModelId = failedModelId
        )
    }

    fun clearActiveModel(type: ModelType) {
        selector.clear(type)
    }

    fun clearAllActiveModels() {
        selector.clearAll()
    }
}
