#include <jni.h>
#include <cstdint>
#include <memory>

#include "api/audio/audio_processing.h"
#include "api/audio/builtin_audio_processing_builder.h"
#include "api/environment/environment_factory.h"

static std::unique_ptr<webrtc::AudioProcessing> apm;

extern "C"
JNIEXPORT void JNICALL
Java_com_rider_noise_1suppression_webrtc_1apm_NoiseSuppressor_nativeStart(
        JNIEnv*,
        jobject) {

    webrtc::AudioProcessing::Config config;

    config.noise_suppression.enabled = true;
    config.noise_suppression.level =
        webrtc::AudioProcessing::Config::NoiseSuppression::kHigh;

    apm = webrtc::BuiltinAudioProcessingBuilder(
        config
    ).Build(
        webrtc::CreateEnvironment()
    );
}

extern "C"
JNIEXPORT jshortArray JNICALL
Java_com_rider_noise_1suppression_webrtc_1apm_NoiseSuppressor_nativeProcess(
        JNIEnv* env,
        jobject,
        jshortArray input,
        jint sampleRate) {

    if (!apm || input == nullptr) {
        return input;
    }

    const jsize size = env->GetArrayLength(input);

    if (size <= 0 || sampleRate <= 0) {
        return input;
    }

    jshort* data = env->GetShortArrayElements(
        input,
        nullptr
    );

    webrtc::AudioFrame frame;

    frame.sample_rate_hz_ = sampleRate;
    frame.num_channels_ = 1;
    frame.samples_per_channel_ = size;

    for (int i = 0; i < size; ++i) {
        frame.mutable_data()[i] = data[i];
    }

    apm->ProcessStream(&frame);

    jshortArray output =
        env->NewShortArray(size);

    env->SetShortArrayRegion(
        output,
        0,
        size,
        frame.data()
    );

    env->ReleaseShortArrayElements(
        input,
        data,
        JNI_ABORT
    );

    return output;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_rider_noise_1suppression_webrtc_1apm_NoiseSuppressor_nativeStop(
        JNIEnv*,
        jobject) {

    apm.reset();
}
