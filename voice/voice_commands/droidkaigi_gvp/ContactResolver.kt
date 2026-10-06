package com.rider.voice_commands.droidkaigi_gvp

interface VoiceCommandExecutor {
    fun execute(command: VoiceCommand): Boolean
}
