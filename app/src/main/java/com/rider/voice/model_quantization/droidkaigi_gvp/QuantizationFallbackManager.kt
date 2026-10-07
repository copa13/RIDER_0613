package com.rider.voice.model_quantization.droidkaigi_gvp

class QuantizationFallbackManager(
    private val compatibilityChecker:
        QuantizationCompatibilityChecker
) {

    fun findFallback(
        availableRamGb: Double,
        models: List<QuantizedModelInfo>
    ): QuantizedModelInfo? {

        return models
            .filter {
                compatibilityChecker.isCompatible(
                    availableRamGb,
                    it
                )
            }
            .sortedBy {
                it.quantization.bitsPerWeight
            }
            .firstOrNull()
    }
}
