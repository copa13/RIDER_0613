package com.rider.voice.model_quantization.droidkaigi_gvp

class QuantizationSelector(
    private val compatibilityChecker:
        QuantizationCompatibilityChecker
) {

    fun select(
        availableRamGb: Double,
        models: List<QuantizedModelInfo>
    ): QuantizedModelInfo? {

        val compatible =
            models.filter {
                compatibilityChecker.isCompatible(
                    availableRamGb,
                    it
                )
            }

        if (compatible.isEmpty()) {
            return null
        }

        return compatible.maxWithOrNull(
            compareBy<QuantizedModelInfo> {
                it.qualityScore
            }.thenByDescending {
                it.quantization.bitsPerWeight
            }
        )
    }
}
