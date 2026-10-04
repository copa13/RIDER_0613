package com.rider.user_speech_stops_tts.sherpa_onnx

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class Feature31BargeInTest {
    @Test
    fun cancelledGenerationCannotEnqueueStaleText() {
        val state = CancellableAiResponseState()
        val spoken = mutableListOf<String>()
        var cancellations = 0
        val token = state.beginResponse()
        state.attachRequest(token, CancellableAiResponseRequest { cancellations++ })

        assertTrue(state.deliverIfCurrent(token, "first") { spoken.add(it) })
        assertTrue(state.cancelActive())
        assertFalse(state.deliverIfCurrent(token, "stale") { spoken.add(it) })

        assertEquals(listOf("first"), spoken)
        assertEquals(1, cancellations)
    }

    @Test
    fun aHandleArrivingAfterCancellationIsCancelledImmediately() {
        val state = CancellableAiResponseState()
        var cancellations = 0
        val token = state.beginResponse()
        assertTrue(state.cancelActive())

        state.attachRequest(token, CancellableAiResponseRequest { cancellations++ })

        assertEquals(1, cancellations)
    }

    @Test
    fun bargeInCancelsResponseAndStopsTtsOnlyOnce() {
        val events = mutableListOf<String>()
        val controller = BargeInController(
            stopTts = { events.add("stop") },
            cancelResponse = { events.add("cancel") }
        )

        controller.onTtsStarted()
        controller.onUserSpeechDetected()
        controller.onUserSpeechDetected()

        assertEquals(listOf("cancel", "stop"), events)
        assertFalse(controller.isTtsPlaying())
    }
}
