package com.rider.voice.modular_engines.droidkaigi_gvp

import java.io.ByteArrayOutputStream

object PcmWavEncoder {

    fun encode(
        samples: FloatArray,
        sampleRate: Int
    ): ByteArray {
        require(samples.isNotEmpty())
        require(sampleRate > 0)

        val pcm = ByteArrayOutputStream(samples.size * 2)

        for (sample in samples) {
            val clamped = sample.coerceIn(-1f, 1f)
            val value = (clamped * 32767f).toInt()

            pcm.write(value and 0xFF)
            pcm.write((value shr 8) and 0xFF)
        }

        val pcmBytes = pcm.toByteArray()
        val output = ByteArrayOutputStream(44 + pcmBytes.size)

        fun writeAscii(value: String) {
            output.write(value.toByteArray(Charsets.US_ASCII))
        }

        fun writeLeShort(value: Int) {
            output.write(value and 0xFF)
            output.write((value shr 8) and 0xFF)
        }

        fun writeLeInt(value: Int) {
            output.write(value and 0xFF)
            output.write((value shr 8) and 0xFF)
            output.write((value shr 16) and 0xFF)
            output.write((value shr 24) and 0xFF)
        }

        writeAscii("RIFF")
        writeLeInt(36 + pcmBytes.size)
        writeAscii("WAVE")
        writeAscii("fmt ")
        writeLeInt(16)
        writeLeShort(1)
        writeLeShort(1)
        writeLeInt(sampleRate)
        writeLeInt(sampleRate * 2)
        writeLeShort(2)
        writeLeShort(16)
        writeAscii("data")
        writeLeInt(pcmBytes.size)
        output.write(pcmBytes)

        return output.toByteArray()
    }
}
