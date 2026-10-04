package com.rider.user_speech_stops_tts.sherpa_onnx

/** A cancellable handle returned by the future RIDER AI transport. */
fun interface CancellableAiResponseRequest {
    fun cancel()
}

/** Provider-neutral contract; this module intentionally implements no AI backend. */
interface CancellableAiResponseClient {
    fun startResponse(
        prompt: String,
        onText: (String) -> Unit,
        onComplete: () -> Unit,
        onFailure: (Throwable) -> Unit
    ): CancellableAiResponseRequest
}

data class ResponseGenerationToken internal constructor(val generation: Long)

/**
 * Owns one current AI response generation. Text is allowed to reach TTS only
 * while its token is current. TTS enqueue callbacks must return quickly.
 */
class CancellableAiResponseState {
    private data class ActiveResponse(
        val token: ResponseGenerationToken,
        var request: CancellableAiResponseRequest? = null
    )

    private val lock = Any()
    private var nextGeneration = 0L
    private var active: ActiveResponse? = null

    fun beginResponse(): ResponseGenerationToken {
        val (oldRequest, token) = synchronized(lock) {
            val old = active?.request
            val next = ResponseGenerationToken(++nextGeneration)
            active = ActiveResponse(next)
            old to next
        }
        cancelQuietly(oldRequest)
        return token
    }

    /** Late handles are cancelled immediately if their generation is stale. */
    fun attachRequest(token: ResponseGenerationToken, request: CancellableAiResponseRequest) {
        val (oldRequest, cancelNew) = synchronized(lock) {
            val current = active
            if (current?.token == token) {
                val old = current.request
                current.request = request
                old to false
            } else {
                null to true
            }
        }
        cancelQuietly(oldRequest)
        if (cancelNew) cancelQuietly(request)
    }

    /** Atomically validates the generation and queues one text/audio chunk. */
    fun deliverIfCurrent(
        token: ResponseGenerationToken,
        text: String,
        enqueueForSpeech: (String) -> Unit
    ): Boolean {
        if (text.isBlank()) return false
        return synchronized(lock) {
            if (active?.token != token) {
                false
            } else {
                enqueueForSpeech(text)
                true
            }
        }
    }

    fun finish(token: ResponseGenerationToken): Boolean = synchronized(lock) {
        if (active?.token != token) {
            false
        } else {
            active = null
            true
        }
    }

    /** Invalidates output first, then cancels the provider request. */
    fun cancelActive(): Boolean {
        val request = synchronized(lock) {
            val current = active ?: return false
            active = null
            current.request
        }
        cancelQuietly(request)
        return true
    }

    fun isCurrent(token: ResponseGenerationToken): Boolean = synchronized(lock) {
        active?.token == token
    }

    /** Convenience adapter for a real provider; it never invents response text. */
    fun startResponse(
        client: CancellableAiResponseClient,
        prompt: String,
        enqueueForSpeech: (String) -> Unit,
        onComplete: () -> Unit = {},
        onFailure: (Throwable) -> Unit = {}
    ): ResponseGenerationToken {
        val token = beginResponse()
        val request = try {
            client.startResponse(
                prompt = prompt,
                onText = { text -> deliverIfCurrent(token, text, enqueueForSpeech) },
                onComplete = { if (finish(token)) onComplete() },
                onFailure = { failure -> if (finish(token)) onFailure(failure) }
            )
        } catch (failure: Exception) {
            if (finish(token)) onFailure(failure)
            return token
        }
        attachRequest(token, request)
        return token
    }

    private fun cancelQuietly(request: CancellableAiResponseRequest?) {
        try {
            request?.cancel()
        } catch (_: Exception) {
            // Output remains invalidated even if a transport's cancellation fails.
        }
    }
}
