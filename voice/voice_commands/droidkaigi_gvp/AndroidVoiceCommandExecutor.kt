package com.rider.voice_commands.droidkaigi_gvp

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.net.Uri
import android.os.BatteryManager
import android.provider.AlarmClock
import android.provider.Settings
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AndroidVoiceCommandExecutor(
    private val context: Context,
    private val onFeedback: (String) -> Unit = {}
) : VoiceCommandExecutor {

    private val resolver = ContactResolver(context)

    override fun execute(command: VoiceCommand): Boolean {
        return when (command) {

            VoiceCommand.LockPhone ->
                lockPhone()

            VoiceCommand.OpenPowerMenu ->
                RiderAccessibilityBridge.powerMenu()

            VoiceCommand.BatteryStatus ->
                batteryStatus()

            VoiceCommand.GetTime ->
                getTime()

            VoiceCommand.GetDate ->
                getDate()

            is VoiceCommand.OpenApp ->
                openApp(command.appName)

            is VoiceCommand.Call ->
                callContact(command.contactName)

            VoiceCommand.DialNumber ->
                openDialer()

            VoiceCommand.CallEmergencyContact ->
                openDialer()

            is VoiceCommand.DraftMessage ->
                draftMessage(
                    command.contactName,
                    command.message
                )

            is VoiceCommand.TextMessage ->
                draftMessage(
                    command.contactName,
                    command.message
                )

            is VoiceCommand.OpenWhatsAppMessage ->
                openWhatsAppMessage(command.contactName)

            is VoiceCommand.SetAlarm ->
                setAlarm(command.timeText)

            is VoiceCommand.WakeAt ->
                setAlarm(command.timeText)

            VoiceCommand.CancelAlarm ->
                openAlarmApp()

            VoiceCommand.ShowAlarms ->
                showAlarms()

            is VoiceCommand.SetTimer ->
                setTimer(command.durationText)

            VoiceCommand.StopTimer ->
                openTimerApp()

            VoiceCommand.SnoozeTimer ->
                openTimerApp()

            VoiceCommand.TimeLeft ->
                openTimerApp()

            VoiceCommand.PlayMusic ->
                mediaKey(AudioManager.KEYCODE_MEDIA_PLAY)

            VoiceCommand.PauseMusic ->
                mediaKey(AudioManager.KEYCODE_MEDIA_PAUSE)

            VoiceCommand.NextSong ->
                mediaKey(AudioManager.KEYCODE_MEDIA_NEXT)

            VoiceCommand.PreviousSong ->
                mediaKey(AudioManager.KEYCODE_MEDIA_PREVIOUS)

            VoiceCommand.StopMusic ->
                mediaKey(AudioManager.KEYCODE_MEDIA_STOP)

            VoiceCommand.VolumeUp ->
                changeVolume(AudioManager.ADJUST_RAISE)

            VoiceCommand.VolumeDown ->
                changeVolume(AudioManager.ADJUST_LOWER)

            VoiceCommand.Mute ->
                muteVolume()

            VoiceCommand.Unmute ->
                unmuteVolume()

            VoiceCommand.FlashlightOn ->
                setFlashlight(true)

            VoiceCommand.FlashlightOff ->
                setFlashlight(false)

            VoiceCommand.FlashlightToggle ->
                toggleFlashlight()

            VoiceCommand.GoBack ->
                RiderAccessibilityBridge.goBack()

            VoiceCommand.GoHome ->
                RiderAccessibilityBridge.goHome()

            VoiceCommand.RecentApps ->
                RiderAccessibilityBridge.recentApps()

            VoiceCommand.Notifications ->
                RiderAccessibilityBridge.notifications()

            VoiceCommand.QuickSettings ->
                RiderAccessibilityBridge.quickSettings()

            VoiceCommand.ScrollDown ->
                RiderAccessibilityBridge.scrollDown()

            VoiceCommand.ScrollUp ->
                RiderAccessibilityBridge.scrollUp()

            VoiceCommand.TapConfirm ->
                RiderAccessibilityBridge.tapConfirm()

            VoiceCommand.TapCancel ->
                RiderAccessibilityBridge.tapCancel()

            VoiceCommand.WifiSettings ->
                openSettings(Settings.ACTION_WIFI_SETTINGS)

            VoiceCommand.BluetoothSettings ->
                openSettings(Settings.ACTION_BLUETOOTH_SETTINGS)

            VoiceCommand.BatterySettings ->
                openSettings(Settings.ACTION_BATTERY_SAVER_SETTINGS)

            VoiceCommand.AccessibilitySettings ->
                openSettings(Settings.ACTION_ACCESSIBILITY_SETTINGS)

            VoiceCommand.NotificationSettings ->
                openSettings(Settings.ACTION_APP_NOTIFICATION_SETTINGS)

            VoiceCommand.LocationSettings ->
                openSettings(Settings.ACTION_LOCATION_SOURCE_SETTINGS)

            VoiceCommand.HotspotSettings ->
                openSettings(Settings.ACTION_WIRELESS_SETTINGS)

            VoiceCommand.StopListening -> {
                onFeedback("Stopping listening")
                true
            }

            VoiceCommand.PauseAssistant -> {
                onFeedback("Pausing assistant")
                true
            }

            VoiceCommand.ResumeAssistant -> {
                onFeedback("Resuming assistant")
                true
            }

            VoiceCommand.Cancel -> {
                onFeedback("Cancelled")
                true
            }

            VoiceCommand.Repeat -> {
                onFeedback("Repeat")
                true
            }

            VoiceCommand.WhatCanYouDo -> {
                onFeedback(
                    "I can control supported phone functions, apps, calls, messages, alarms, timers, media, volume, flashlight, navigation and settings."
                )
                true
            }
        }
    }

    private fun lockPhone(): Boolean {
        val service = RiderAccessibilityService.getInstance()

        if (service == null) {
            onFeedback("Accessibility service is not enabled")
            return false
        }

        return try {
            val method = service.javaClass.methods.firstOrNull {
                it.name == "performGlobalAction"
            }

            val result =
                method?.invoke(
                    service,
                    android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_LOCK_SCREEN
                ) as? Boolean ?: false

            if (!result) {
                onFeedback("Unable to lock phone")
            }

            result
        } catch (_: Exception) {
            onFeedback("Unable to lock phone")
            false
        }
    }

    private fun batteryStatus(): Boolean {
        val batteryManager =
            context.getSystemService(Context.BATTERY_SERVICE)
                    as BatteryManager

        val level = batteryManager.getIntProperty(
            BatteryManager.BATTERY_PROPERTY_CAPACITY
        )

        onFeedback("Battery is at $level percent")
        return true
    }

    private fun getTime(): Boolean {
        val time = SimpleDateFormat(
            "h:mm a",
            Locale.getDefault()
        ).format(Date())

        onFeedback("The time is $time")
        return true
    }

    private fun getDate(): Boolean {
        val date = SimpleDateFormat(
            "d MMMM yyyy",
            Locale.getDefault()
        ).format(Date())

        onFeedback("The date is $date")
        return true
    }

    private fun openApp(appName: String): Boolean {
        val packageManager = context.packageManager

        val packages = packageManager
            .getInstalledApplications(0)

        val target = packages.firstOrNull { app ->
            val label =
                packageManager.getApplicationLabel(app)
                    .toString()

            label.equals(
                appName,
                ignoreCase = true
            ) ||
                label.contains(
                    appName,
                    ignoreCase = true
                )
        } ?: run {
            onFeedback("App not found")
            return false
        }

        val launchIntent =
            packageManager.getLaunchIntentForPackage(
                target.packageName
            ) ?: return false

        launchIntent.addFlags(
            Intent.FLAG_ACTIVITY_NEW_TASK
        )

        context.startActivity(launchIntent)

        return true
    }

    private fun callContact(name: String): Boolean {
        val contact = resolver.findContact(name)

        if (contact == null) {
            onFeedback("Contact not found")
            return false
        }

        val intent = Intent(
            Intent.ACTION_DIAL,
            Uri.parse("tel:${Uri.encode(contact.phoneNumber)}")
        )

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)

        return true
    }

    private fun openDialer(): Boolean {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        context.startActivity(intent)

        return true
    }

    private fun draftMessage(
        contactName: String,
        message: String
    ): Boolean {

        val contact = resolver.findContact(contactName)

        if (contact == null) {
            onFeedback("Contact not found")
            return false
        }

        val uri =
            Uri.parse("smsto:${Uri.encode(contact.phoneNumber)}")

        val intent = Intent(
            Intent.ACTION_SENDTO,
            uri
        ).apply {
            putExtra("sms_body", message)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        context.startActivity(intent)

        return true
    }

    private fun openWhatsAppMessage(
        contactName: String
    ): Boolean {

        val contact = resolver.findContact(contactName)

        if (contact == null) {
            onFeedback("Contact not found")
            return false
        }

        val number = contact.phoneNumber
            .replace("+", "")
            .replace(" ", "")
            .replace("-", "")
            .replace("(", "")
            .replace(")", "")

        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://wa.me/$number")
        ).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        context.startActivity(intent)

        return true
    }

    private fun setAlarm(timeText: String): Boolean {
        val time =
            TimeParser.parseAlarmHourMinute(timeText)
                ?: run {
                    onFeedback("Invalid alarm time")
                    return false
                }

        val intent = Intent(
            AlarmClock.ACTION_SET_ALARM
        ).apply {
            putExtra(AlarmClock.EXTRA_HOUR, time.first)
            putExtra(AlarmClock.EXTRA_MINUTES, time.second)
            putExtra(AlarmClock.EXTRA_SKIP_UI, false)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        context.startActivity(intent)

        return true
    }

    private fun showAlarms(): Boolean {
        val intent = Intent(
            AlarmClock.ACTION_SHOW_ALARMS
        ).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        context.startActivity(intent)

        return true
    }

    private fun openAlarmApp(): Boolean {
        return try {
            val intent = Intent(
                AlarmClock.ACTION_SHOW_ALARMS
            ).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

            context.startActivity(intent)
            true
        } catch (_: Exception) {
            false
        }
    }

    private fun setTimer(durationText: String): Boolean {
        val millis =
            TimeParser.parseDurationMillis(durationText)
                ?: run {
                    onFeedback("Invalid timer duration")
                    return false
                }

        val seconds = millis / 1000L

        val intent = Intent(
            AlarmClock.ACTION_SET_TIMER
        ).apply {
            putExtra(
                AlarmClock.EXTRA_LENGTH,
                seconds.toInt()
            )
            putExtra(
                AlarmClock.EXTRA_SKIP_UI,
                false
            )
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        context.startActivity(intent)

        return true
    }

    private fun openTimerApp(): Boolean {
        return try {
            val intent = Intent(
                AlarmClock.ACTION_SHOW_TIMERS
            ).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

            context.startActivity(intent)
            true
        } catch (_: Exception) {
            false
        }
    }

    private fun mediaKey(keyCode: Int): Boolean {
        val audioManager =
            context.getSystemService(
                Context.AUDIO_SERVICE
            ) as AudioManager

        val down = android.view.KeyEvent(
            android.view.KeyEvent.ACTION_DOWN,
            keyCode
        )

        val up = android.view.KeyEvent(
            android.view.KeyEvent.ACTION_UP,
            keyCode
        )

        audioManager.dispatchMediaKeyEvent(down)
        audioManager.dispatchMediaKeyEvent(up)

        return true
    }

    private fun changeVolume(direction: Int): Boolean {
        val audioManager =
            context.getSystemService(
                Context.AUDIO_SERVICE
            ) as AudioManager

        audioManager.adjustStreamVolume(
            AudioManager.STREAM_MUSIC,
            direction,
            AudioManager.FLAG_SHOW_UI
        )

        return true
    }

    private fun muteVolume(): Boolean {
        val audioManager =
            context.getSystemService(
                Context.AUDIO_SERVICE
            ) as AudioManager

        audioManager.adjustStreamVolume(
            AudioManager.STREAM_MUSIC,
            AudioManager.ADJUST_MUTE,
            AudioManager.FLAG_SHOW_UI
        )

        return true
    }

    private fun unmuteVolume(): Boolean {
        val audioManager =
            context.getSystemService(
                Context.AUDIO_SERVICE
            ) as AudioManager

        audioManager.adjustStreamVolume(
            AudioManager.STREAM_MUSIC,
            AudioManager.ADJUST_UNMUTE,
            AudioManager.FLAG_SHOW_UI
        )

        return true
    }

    private fun setFlashlight(enabled: Boolean): Boolean {
        val cameraManager =
            context.getSystemService(
                Context.CAMERA_SERVICE
            ) as CameraManager

        val cameraId =
            findFlashlightCamera(cameraManager)
                ?: return false

        return try {
            cameraManager.setTorchMode(
                cameraId,
                enabled
            )
            true
        } catch (_: Exception) {
            false
        }
    }

    private fun toggleFlashlight(): Boolean {
        val cameraManager =
            context.getSystemService(
                Context.CAMERA_SERVICE
            ) as CameraManager

        val cameraId =
            findFlashlightCamera(cameraManager)
                ?: return false

        return try {
            val characteristics =
                cameraManager.getCameraCharacteristics(
                    cameraId
                )

            val available =
                characteristics.get(
                    CameraCharacteristics.FLASH_INFO_AVAILABLE
                ) == true

            if (!available) return false

            cameraManager.setTorchMode(
                cameraId,
                true
            )

            true
        } catch (_: Exception) {
            false
        }
    }

    private fun findFlashlightCamera(
        cameraManager: CameraManager
    ): String? {
        return try {
            cameraManager.cameraIdList.firstOrNull { id ->
                val characteristics =
                    cameraManager.getCameraCharacteristics(id)

                characteristics.get(
                    CameraCharacteristics.FLASH_INFO_AVAILABLE
                ) == true
            }
        } catch (_: Exception) {
            null
        }
    }

    private fun openSettings(action: String): Boolean {
        return try {
            val intent = Intent(action).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

            context.startActivity(intent)
            true
        } catch (_: Exception) {
            false
        }
    }
}
