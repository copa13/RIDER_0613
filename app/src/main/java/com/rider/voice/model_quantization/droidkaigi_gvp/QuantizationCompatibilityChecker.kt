package com.rider.voice.model_quantization.droidkaigi_gvp

class QuantizationCompatibilityChecker {

    fun isCompatible(
        availableRamGb: Double,
        model: QuantizedModelInfo
    ): Boolean {
        return availableRamGb >= model.minimumRamGb
    }
}
