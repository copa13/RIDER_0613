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

# VISHU / RIDER - Feature #7
# Wake-Word Mode

## Software/Projects Found

1. sherpa-onnx - Keyword Spotter
2. openWakeWord
3. Porcupine
4. Vosk/Kaldi Keyword Spotting
5. Mycroft Precise
6. sherpa-onnx based Android Wake Word projects

## Total Options

6

## Selected Software

sherpa-onnx - Keyword Spotter

## Reason

RIDER already uses sherpa-onnx for Continuous Listening,
VAD, and End-of-Speech Detection.

The sherpa-onnx Keyword Spotter provides an offline
Android-compatible wake-word detection pipeline.

It can be connected to the existing RIDER speech pipeline
without adding a separate wake-word engine.

## RIDER Wake Word

Hello Rider

## RIDER Usage

Feature: #7 Wake-Word Mode

Selected: sherpa-onnx - Keyword Spotter

Status: Reference code stored
Build integration: Not started
# VISHU / RIDER - Feature #8
# Wake-Word Without Listening

## Software/Projects Found

1. sherpa-onnx - Continuous/Streaming ASR
2. Takeout Assistant
3. Android Speech Recognition
4. Vosk Android Wake-Word Sample
5. Offline Android Speech Recognition
6. NOVA

## Total Options

6

## Selected Software

sherpa-onnx - Continuous/Streaming ASR

## Reason

RIDER already uses sherpa-onnx for:

- Continuous Listening
- VAD
- End-of-Speech Detection
- Wake-Word Mode

Using the same sherpa-onnx speech pipeline for
wake-word-free listening keeps the system modular
and avoids adding another speech recognition engine.

It also supports Android and local/offline processing.

## RIDER Usage

Feature: #8 Wake-Word Without Listening

Selected: sherpa-onnx - Continuous/Streaming ASR

Status: Reference identified
Build integration: Not started
