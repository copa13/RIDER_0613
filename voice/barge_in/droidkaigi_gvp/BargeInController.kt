# VISHU / RIDER - Feature #31
# Barge-in

## Software / Projects Found

1. DroidKaigi 2026 GVP
2. FluxVoice
3. VoiceChat
4. voice-interruption-handler
5. AI Assistant for Android

## Selected Software

DroidKaigi 2026 GVP

## Reason

Android-compatible voice pipeline using VAD,
TTS interruption and real-time voice interaction.

It demonstrates barge-in behavior where the user
can speak while RIDER is speaking.

## RIDER Usage

RIDER TTS is speaking
        ↓
User starts speaking
        ↓
VAD detects user speech
        ↓
Current TTS stops immediately
        ↓
New user speech is processed
        ↓
RIDER generates the new response

## Integration

Use the existing RIDER VAD and TTS control
components.

Feature #31 adds the real-time interruption
controller between speech detection and TTS.

## Status

Feature: #31 Barge-in

Selected: DroidKaigi 2026 GVP

Status: Reference identified
Build integration: Not started
