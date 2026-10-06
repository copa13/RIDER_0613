package com.rider.model_selection.droidkaigi_gvp

class ModelRegistry {

    private val models = linkedMapOf<String, ModelInfo>()

    fun register(model: ModelInfo) {
        models[model.id] = model
    }

    fun remove(modelId: String) {
        models.remove(modelId)
    }

    fun find(modelId: String): ModelInfo? {
        return models[modelId]
    }

    fun getAll(): List<ModelInfo> {
        return models.values.toList()
    }

    fun getByType(type: ModelType): List<ModelInfo> {
        return models.values.filter {
            it.type == type
        }
    }

    fun getInstalled(type: ModelType): List<ModelInfo> {
        return models.values.filter {
            it.type == type &&
                it.installed &&
                it.enabled
        }
    }
}
