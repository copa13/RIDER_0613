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

**Status: BLOCKED — the component path is implemented, but the real RIDER interruption flow is not end-to-end integrated or verified.**

Implemented for #31 only:

- RiderVad uses sherpa-onnx Silero VAD v5; no energy/amplitude threshold is used.
- An Android AudioRecord monitor connects microphone PCM through the Feature #29 echo-reference interface to Silero VAD.
- The existing Feature #21 BargeInController is reused; its Feature #31 session calls the existing Feature #20 TtsPauseStop MediaPlayer to stop real playback and invalidates active response state on speech onset. The duplicate amplitude-threshold controller was removed.
- CancellableAiResponseState provides cancellable request handles, generation tokens, and stale-chunk rejection.
- A minimal Android library module downloads the pinned sherpa-onnx runtime and model with SHA-256 verification; it does not add an app UI or AI provider.
- Unit tests were added for cancellation, stale chunks, and one-shot TTS stop behavior.

Remaining blockers:

- No real AI request/stream or TTS playback runtime exists in the repository. There is no live response/player to cancel or stale audio stream to test; no fake backend or response generation was added.
- Feature #29's Aec3Processor native dependency is missing. The wrapper is wired to the echo-reference interface but cannot run until that dependency is supplied.
- The actual microphone → AEC → VAD → AI cancellation → TTS stop path still needs integration and testing on the Android host/device.

Do not mark Feature #31 complete until the real RIDER host flow is connected and an Android interruption test passes. See project_info/feature_31_barge_in.md.
