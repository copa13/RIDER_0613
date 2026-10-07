package com.rider.voice.model_quantization.droidkaigi_gvp

data class QuantizationProfile(
    val quantization: QuantizationType,
    val minimumRamGb: Double,
    val preferred: Boolean
)
