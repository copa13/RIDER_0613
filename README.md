# VISHU Android Voice System

An Android-based voice system for VISHU.

## Project Goal

Build the VISHU voice system step by step for Android phones.

## Development Rules

- Every new feature must be approved by Rider before implementation.
- No file, feature, dependency, or configuration will be created, changed, or deleted without approval.
- GitHub projects may be studied as technical references.
- Do not copy another project's concept.
- Do not blindly copy-paste another project's code.
- Reusable third-party components may be used only after checking their license.
- Each feature will be tested before being connected to VISHU.
- The project must remain compatible with Android phones.

## Feature Pool

The project will gradually select features from the saved 80-feature list.

## Update Log

### Initial Setup
- Project README created.
- Development rules established.
- Feature-by-feature development planned.

## Status

Project setup: In progress

## Feature #31 — Barge-in

**Status: BLOCKED — not implemented or end-to-end verified.**

Repository inspection of main found these blockers:

- voice/continuous_listening/sherpa_onnx/Vad.kt is a placeholder: RiderVad.isSpeechDetected() always returns false and does not call sherpa-onnx VAD.
- The #21 BargeInController only tracks TTS state and calls stopTts; it has no active AI-response cancellation or stale-output protection.
- voice/barge_in/droidkaigi_gvp/RiderBargeInController.kt uses an amplitude threshold instead of real VAD and is not connected to the VAD/audio/STT/TTS path.
- The tracked repository has no Android manifest, application entry point, Gradle root/settings/wrapper, or AI-response implementation. app/build.gradle.kts only references app/libs/libwebrtc_arm64.aar, which is not tracked.
- No sherpa-onnx Android native library or VAD model is tracked. Existing STT/TTS classes are wrappers; there is no application flow connecting microphone input, AI response generation, and audible TTS playback.

Without the actual AI request/stream and app playback path, there is no live response to cancel or output stream to guard against stale audio. Adding a fabricated AI implementation or claiming the existing stubs complete would violate the feature requirements. Do not mark #31 complete until the real application/AI path and required Android runtime artifacts are available and the interruption-to-new-turn flow is verified.
