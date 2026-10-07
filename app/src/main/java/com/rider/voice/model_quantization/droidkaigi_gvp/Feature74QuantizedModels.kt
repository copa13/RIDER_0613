package com.rider.voice.model_quantization.droidkaigi_gvp

class Feature74QuantizedModels {

    private val registry =
        QuantizationRegistry()

    private val checker =
        QuantizationCompatibilityChecker()

    private val selector =
        QuantizationSelector(checker)

    private val fallbackManager =
        QuantizationFallbackManager(checker)

    fun registerModel(
        model: QuantizedModelInfo
    ) {
        registry.register(model)
    }

    fun removeModel(
        modelId: String
    ) {
        registry.remove(modelId)
    }

    fun getModels():
        List<QuantizedModelInfo> {
        return registry.getAll()
    }

    fun getModels(
        type: QuantizationType
    ): List<QuantizedModelInfo> {
        return registry.getByQuantization(type)
    }

    fun selectQuantization(
        availableRamGb: Double
    ): QuantizedModelInfo? {

        return selector.select(
            availableRamGb,
            registry.getAll()
        )
    }

    fun selectFallback(
        availableRamGb: Double
    ): QuantizedModelInfo? {

        return fallbackManager.findFallback(
            availableRamGb,
            registry.getAll()
        )
    }
}
