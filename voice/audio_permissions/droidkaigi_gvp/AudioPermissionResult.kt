package com.rider.audio_permissions.droidkaigi_gvp

sealed class AudioPermissionResult {

    data object Granted : AudioPermissionResult()

    data object RequestRequired : AudioPermissionResult()

    data object Denied : AudioPermissionResult()

    data object Blocked : AudioPermissionResult()
}
