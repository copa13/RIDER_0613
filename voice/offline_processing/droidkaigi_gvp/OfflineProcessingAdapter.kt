package com.rider.offline_processing.droidkaigi_gvp

import com.rider.voice_commands.droidkaigi_gvp.VoiceCommand
import com.rider.tool_calling.droidkaigi_gvp.Feature50ToolCallingAdapter
import com.rider.tool_calling.droidkaigi_gvp.ToolCallResult

class OfflineProcessingAdapter(
    private val toolCallingAdapter: Feature50ToolCallingAdapter
) {

    fun process(command: VoiceCommand): OfflineProcessingResult {

        if (!OfflineProcessingPolicy.allowsOfflineProcessing()) {
            return OfflineProcessingResult.Blocked(
                reason = "Offline processing is disabled."
            )
        }

        return try {

            val result: ToolCallResult =
                toolCallingAdapter.executeCommand(command)

            when (result) {

                is ToolCallResult.Success -> {
                    OfflineProcessingResult.Processed(
                        offline = true
                    )
                }

                is ToolCallResult.Failed -> {
                    OfflineProcessingResult.Failed(
                        reason = result.message
                    )
                }

                is ToolCallResult.Invalid -> {
                    OfflineProcessingResult.Failed(
                        reason = result.message
                    )
                }
            }

        } catch (error: Exception) {

            OfflineProcessingResult.Failed(
                reason = error.message
                    ?: "Offline processing failed."
            )
        }
    }
}
