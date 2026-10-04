package com.rider.barge_in.runtime

/**
 * Full-duplex echo-reference stage. Playback PCM must be submitted before it is
 * rendered, and microphone PCM must be returned after self-speech suppression.
 */
interface BargeInEchoReference {
    fun start(sampleRate: Int = 16000)
    fun processRenderAudio(samples: ShortArray)
    fun processMicrophoneAudio(samples: ShortArray): ShortArray
    fun stop()
}
