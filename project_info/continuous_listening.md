# VISHU / RIDER - Feature #1
# Continuous Listening

## Software/Projects Found

1. sherpa-onnx
2. speech-android
3. android-local-transcribe
4. SherpaOnnx Android Streaming ASR

## Selected Software

sherpa-onnx

## Reason

sherpa-onnx provides local streaming ASR and Android support.
It also provides Android examples for streaming speech recognition
and VAD.

## RIDER Usage

Feature: #1 Continuous Listening

Selected: sherpa-onnx

Status: Reference code stored
Build integration: Not started

#5 VAD

Software: sherpa-onnx
VAD model: Silero VAD
Status: Selected

# VISHU / RIDER - Feature #6
# End-of-Speech Detection

## Software/Projects Found

1. sherpa-onnx - Endpoint Detection
2. sherpa-onnx - Silero VAD based endpointing
3. Smart Turn v3.2
4. speech-android - Smart Turn
5. android-vad - Silence Duration Endpointing
6. Silero VAD
7. WebRTC VAD
8. TEN VAD

## Total Options

8

## Selected Software

sherpa-onnx - Endpoint Detection

## Reason

RIDER already uses sherpa-onnx for Continuous Listening and VAD.

Endpoint Detection is supported by sherpa-onnx
and can detect when the user has stopped speaking.

It can be combined with the existing speech pipeline
without adding a separate endpoint detection system.

## RIDER Usage

Feature: #6 End-of-Speech Detection

Selected: sherpa-onnx - Endpoint Detection

Status: Reference identified
Build integration: Not started
