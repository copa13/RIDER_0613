# VISHU / RIDER - Feature #29
# Self-Speech Rejection

## Software / Projects Found

1. webrtc-aec3-kmp
2. android-webrtc-aec3
3. webrtc-android-jni
4. webrtc-based-android-aecm
5. webrtc-audio-processing
6. Custom-AEC-using-WebRTC

## Selected Software

webrtc-aec3-kmp

## Reason

Android-compatible WebRTC AEC3 implementation
with ARM64/ARMv7 support and JNI integration.

AEC3 can use RIDER's speaker/render audio as
a reference and reduce the same audio when it
returns through the microphone.

## RIDER Usage

RIDER speaks:
TTS → Speaker → Render Reference

Microphone:
User Voice + RIDER Self Voice

AEC3:
Removes/reduces RIDER's own speaker echo

Result:
Cleaner user speech → STT

## Integration

Feature #22 WebRTC APM pipeline will remain.

Feature #29 will add the AEC3/render-reference
stage required for self-speech rejection.

## Status

Feature: #29 Self-Speech Rejection

Selected: webrtc-aec3-kmp

Status: Reference identified
Build integration: Not started
