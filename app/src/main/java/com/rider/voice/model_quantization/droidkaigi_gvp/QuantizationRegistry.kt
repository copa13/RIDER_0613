package com.rider.voice.model_quantization.droidkaigi_gvp

class QuantizationRegistry {

    private val models =
        linkedMapOf<String, QuantizedModelInfo>()

    fun register(
        model: QuantizedModelInfo
    ) {
        models[model.modelId] = model
    }

    fun remove(
        modelId: String
    ) {
        models.remove(modelId)
    }

    fun find(
        modelId: String
    ): QuantizedModelInfo? {
        return models[modelId]
    }

    fun getAll():
        List<QuantizedModelInfo> {
        return models.values.toList()
    }

    fun getByQuantization(
        type: QuantizationType
    ): List<QuantizedModelInfo> {
        return models.values.filter {
            it.quantization == type
        }
    }
}
