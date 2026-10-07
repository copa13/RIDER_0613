package com.rider.voice.model_ram_selection.droidkaigi_gvp

import android.app.ActivityManager
import android.content.Context

class AndroidRamReader(
    private val context: Context
) {

    fun read(): DeviceRamInfo {

        val activityManager =
            context.getSystemService(
                Context.ACTIVITY_SERVICE
            ) as ActivityManager

        val memoryInfo =
            ActivityManager.MemoryInfo()

        activityManager.getMemoryInfo(
            memoryInfo
        )

        return DeviceRamInfo(
            totalRamBytes =
                memoryInfo.totalMem,
            availableRamBytes =
                memoryInfo.availMem
        )
    }
}
