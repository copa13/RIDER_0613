// voice/self_speech_rejection/webrtc_aec3/SelfSpeechRejection.kt

package com.rider.self_speech_rejection.webrtc_aec3

import com.rider.barge_in.runtime.BargeInEchoReference
import com.webrtc.aec3.Aec3Processor

/** Feature #29 adapter for the Feature #31 render-reference/microphone path. */
class SelfSpeechRejection : BargeInEchoReference {
    private var processor: Aec3Processor? = null

    override fun start(sampleRate: Int) {
        if (processor != null) return

        processor = Aec3Processor(
            sampleRate = sampleRate,
            channels = 1
        )
    }

    override fun processRenderAudio(samples: ShortArray) {
        checkNotNull(processor) {
            "Self-speech rejection is not started"
        }
        processor?.processRender(samples)
    }

    override fun processMicrophoneAudio(samples: ShortArray): ShortArray {
        checkNotNull(processor) {
            "Self-speech rejection is not started"
        }
        return processor!!.processCapture(samples)
    }

    override fun stop() {
        processor?.release()
        processor = null
    }
}
