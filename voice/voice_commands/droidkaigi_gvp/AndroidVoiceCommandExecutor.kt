package com.rider.voice_commands.droidkaigi_gvp

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.provider.Settings

class AndroidVoiceCommandExecutor(
    private val context: Context,
    private val appLauncher: (String) -> Boolean,
    private val accessibilityAction: (VoiceCommand) -> Boolean,
    private val phoneAction: (VoiceCommand.Call) -> Boolean,
    private val messageAction: (VoiceCommand.MessageDraft) -> Boolean,
    private val timerAction: (VoiceCommand.SetTimer) -> Boolean,
    private val alarmAction: (VoiceCommand.SetAlarm) -> Boolean,
    private val lockAction: () -> Boolean,
    private val flashlightAction: (Boolean) -> Boolean
) : VoiceCommandExecutor {

    private val audioManager: AudioManager =
        context.getSystemService(Context.AUDIO_SERVICE)
            as AudioManager

    override fun execute(
        command: VoiceCommand
    ): Boolean {

        return when (command) {

            is VoiceCommand.OpenApp -> {
                appLauncher(command.appName)
            }

            VoiceCommand.VolumeUp -> {
                audioManager.adjustVolume(
                    AudioManager.ADJUST_RAISE,
                    AudioManager.FLAG_SHOW_UI
                )
                true
            }

            VoiceCommand.VolumeDown -> {
                audioManager.adjustVolume(
                    AudioManager.ADJUST_LOWER,
                    AudioManager.FLAG_SHOW_UI
                )
                true
            }

            VoiceCommand.Mute -> {
                audioManager.adjustVolume(
                    AudioManager.ADJUST_TOGGLE_MUTE,
                    AudioManager.FLAG_SHOW_UI
                )
                true
            }

            VoiceCommand.FlashlightOn -> {
                flashlightAction(true)
            }

            VoiceCommand.FlashlightOff -> {
                flashlightAction(false)
            }

            VoiceCommand.LockPhone -> {
                lockAction()
            }

            VoiceCommand.OpenSettings -> {
                context.startActivity(
                    Intent(Settings.ACTION_SETTINGS)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                )
                true
            }

            VoiceCommand.OpenPowerMenu,
            VoiceCommand.GoBack,
            VoiceCommand.GoHome,
            VoiceCommand.OpenRecents,
            VoiceCommand.OpenNotifications,
            VoiceCommand.OpenQuickSettings -> {
                accessibilityAction(command)
            }

            is VoiceCommand.Call -> {
                phoneAction(command)
            }

            is VoiceCommand.MessageDraft -> {
                messageAction(command)
            }

            is VoiceCommand.SetTimer -> {
                timerAction(command)
            }

            is VoiceCommand.SetAlarm -> {
                alarmAction(command)
            }

            VoiceCommand.Stop -> {
                true
            }
        }
    }
}
