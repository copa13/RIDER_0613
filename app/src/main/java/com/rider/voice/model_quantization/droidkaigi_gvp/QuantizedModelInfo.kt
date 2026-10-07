package com.rider.voice.model_quantization.droidkaigi_gvp

data class QuantizedModelInfo(
    val modelId: String,
    val quantization: QuantizationType,
    val parameterCountBillions: Double,
    val fileSizeBytes: Long,
    val minimumRamGb: Double,
    val recommendedRamGb: Double,
    val qualityScore: Double
)
