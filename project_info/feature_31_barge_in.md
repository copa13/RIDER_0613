# Feature #31 — Barge-in

## Implemented component path

- Microphone: Android AudioRecord, mono PCM16 at 16 kHz.
- Self-speech rejection: the capture frames pass through the Feature #29 render-reference interface before VAD. TTS render PCM must be sent to the same interface before speaker playback.
- Speech detection: the existing RiderVad wrapper now invokes sherpa-onnx Silero VAD v5 with 512-sample windows. No amplitude/energy threshold is used.
- Interruption: speech onset calls the existing Feature #21 BargeInController. It invalidates/cancels the current response and then invokes the host TTS stop callback.
- Stale output: CancellableAiResponseState issues generation tokens, cancels the provider request, rejects text from stale tokens, and cancels handles attached after interruption.
- Android component setup: the app module is a small Android library, not a full RIDER application. Its build downloads the pinned sherpa-onnx AAR and Silero model and validates both SHA-256 hashes.

## Host integration contract

The eventual RIDER app must request RECORD_AUDIO at runtime, create RiderBargeInSession with the Feature #29 implementation, start the session, call onTtsStarted/onTtsStopped around actual playback, and route the actual 16 kHz mono render PCM through onTtsRenderAudio before it reaches the speaker. The real AI provider must implement CancellableAiResponseClient and route text chunks through CancellableAiResponseState.startResponse; its request handle must abort the provider's in-flight response. TTS stop must synchronously clear/stop its active player or audio track. No provider, generated answer, or playback implementation is supplied here.

## Remaining blockers

- The repository still has no real AI request/stream or TTS audio-output runtime. The cancellation contract and generation guard are ready, but there is no live response or player to wire and verify.
- Feature #29's Aec3Processor dependency/binary is not tracked. The adapter now implements the required interface, but the class cannot be built or instantiated until that real native dependency is provided.
- There is no Android device/emulator test in this repository. Unit tests cover interruption ordering and stale-response gating; the microphone/VAD/AEC/TTS flow still requires an Android host and real audio artifacts.

Build requirement: Gradle 8.11.1, JDK 17, and Android SDK platform 35. Run gradle :app:testDebugUnitTest after the first verified runtime download. This workspace had no Java, Gradle, Android SDK, or adb available, so the added tests and Android build were not executed here.

Feature #31 remains BLOCKED and must not be marked complete until the real host path above is integrated and an Android interruption test passes.
