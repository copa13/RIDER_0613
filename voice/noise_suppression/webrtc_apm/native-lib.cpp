#include <jni.h>
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
JNIEXPORT void JNICALL
Java_com_rider_noise_1suppression_webrtc_1apm_NoiseSuppressor_nativeStop(
        JNIEnv*,
        jobject) {

    apm.reset();
}
