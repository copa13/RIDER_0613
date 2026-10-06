package com.rider.audio_permissions.droidkaigi_gvp

class AudioPermissionGate(
    private val permissions: Feature61AudioPermissions
) {

    fun canStartVoicePipeline(): Boolean {
        return permissions.isMicrophoneGranted()
    }

    fun requirePermissionBeforeListening(): AudioPermissionState {

        return if (permissions.isMicrophoneGranted()) {
            AudioPermissionState.GRANTED
        } else {
            permissions.getState()
        }
    }
}
