package com.rider.noise_suppression.webrtc_apm

class NoiseSuppressor {

    private var initialized = false

    fun start() {
        if (initialized) return

        nativeStart()
        initialized = true
    }

    fun process(
        samples: ShortArray,
        sampleRate: Int
    ): ShortArray {

        check(initialized) {
            "Noise suppressor is not started"
        }

        return nativeProcess(
            samples,
            sampleRate
        )
    }

    fun stop() {
        if (!initialized) return

        nativeStop()
        initialized = false
    }

    private external fun nativeStart()

    private external fun nativeProcess(
        samples: ShortArray,
        sampleRate: Int
    ): ShortArray

    private external fun nativeStop()
}
