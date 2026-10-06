package com.rider.audio_permissions.droidkaigi_gvp

import android.app.Activity
import android.content.Context

class Feature61AudioPermissions(
    context: Context
) {

    private val manager = AudioPermissionManager(context)

    fun getState(): AudioPermissionState {
        return manager.getState()
    }

    fun isMicrophoneGranted(): Boolean {
        return manager.isGranted()
    }

    fun requestMicrophonePermission(activity: Activity) {
        manager.requestPermission(activity)
    }

    fun handlePermissionResult(
        requestCode: Int,
        grantResults: IntArray
    ): AudioPermissionResult {
        return manager.handlePermissionResult(
            requestCode,
            grantResults
        )
    }
}
