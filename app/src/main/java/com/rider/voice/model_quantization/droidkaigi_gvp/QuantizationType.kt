package com.rider.voice.model_quantization.droidkaigi_gvp

enum class QuantizationType(
    val bitsPerWeight: Double
) {
    Q2_K(3.35),
    Q3_K_S(3.50),
    Q3_K_M(3.91),
    Q3_K_L(4.27),
    Q4_K_S(4.58),
    Q4_K_M(4.85),
    Q5_K_S(5.54),
    Q5_K_M(5.69),
    Q6_K(6.56),
    Q8_0(8.50),

    IQ2_XXS(2.06),
    IQ2_XS(2.31),
    IQ2_S(2.50),
    IQ2_M(2.70),
    IQ3_XXS(3.06),
    IQ3_XS(3.30),
    IQ4_XS(4.25),
    IQ4_NL(4.50)
}
