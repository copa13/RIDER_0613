package com.rider.offline_processing.droidkaigi_gvp

import com.rider.voice_commands.droidkaigi_gvp.VoiceCommand
import com.rider.tool_calling.droidkaigi_gvp.Feature50ToolCallingAdapter

class Feature52OfflineProcessing(
    toolCallingAdapter: Feature50ToolCallingAdapter
) {

    private val adapter = OfflineProcessingAdapter(
        toolCallingAdapter = toolCallingAdapter
    )

    fun process(command: VoiceCommand): OfflineProcessingResult {
        return adapter.process(command)
    }
}
