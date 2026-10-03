// voice/self_speech_rejection/webrtc_aec3/SelfSpeechRejection.kt

package com.rider.self_speech_rejection.webrtc_aec3

import com.webrtc.aec3.Aec3Processor

class SelfSpeechRejection {

    private var processor: Aec3Processor? = null

    fun start(sampleRate: Int = 16000) {
        if (processor != null) return

        processor = Aec3Processor(
            sampleRate = sampleRate,
            channels = 1
        )
    }

    fun processRenderAudio(
        samples: ShortArray
    ) {
        checkNotNull(processor) {
            "Self-speech rejection is not started"
        }

        processor?.processRender(
            samples
        )
    }

    fun processMicrophoneAudio(
        samples: ShortArray
    ): ShortArray {
        checkNotNull(processor) {
            "Self-speech rejection is not started"
        }

        return processor!!.processCapture(
            samples
        )
    }

    fun stop() {
        processor?.release()
        processor = null
    }
}
