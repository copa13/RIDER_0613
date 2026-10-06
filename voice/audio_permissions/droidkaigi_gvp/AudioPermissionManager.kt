package com.rider.audio_permissions.droidkaigi_gvp

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class AudioPermissionManager(
    private val context: Context
) {

    companion object {
        const val REQUEST_RECORD_AUDIO = 6101
    }

    fun getState(): AudioPermissionState {

        val granted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED

        if (granted) {
            return AudioPermissionState.GRANTED
        }

        if (context !is Activity) {
            return AudioPermissionState.REQUEST_REQUIRED
        }

        return if (
            ActivityCompat.shouldShowRequestPermissionRationale(
                context,
                Manifest.permission.RECORD_AUDIO
            )
        ) {
            AudioPermissionState.DENIED
        } else {
            AudioPermissionState.REQUEST_REQUIRED
        }
    }

    fun isGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(activity: Activity) {

        if (isGranted()) {
            return
        }

        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.RECORD_AUDIO),
            REQUEST_RECORD_AUDIO
        )
    }

    fun handlePermissionResult(
        requestCode: Int,
        grantResults: IntArray
    ): AudioPermissionResult {

        if (requestCode != REQUEST_RECORD_AUDIO) {
            return AudioPermissionResult.Denied
        }

        if (
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            return AudioPermissionResult.Granted
        }

        return if (context is Activity) {

            val canAskAgain =
                ActivityCompat.shouldShowRequestPermissionRationale(
                    context,
                    Manifest.permission.RECORD_AUDIO
                )

            if (canAskAgain) {
                AudioPermissionResult.Denied
            } else {
                AudioPermissionResult.Blocked
            }

        } else {
            AudioPermissionResult.Denied
        }
    }
}
