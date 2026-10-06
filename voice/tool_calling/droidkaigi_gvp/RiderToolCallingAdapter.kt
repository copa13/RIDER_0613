package com.rider.tool_calling.droidkaigi_gvp

import com.rider.voice_commands.droidkaigi_gvp.VoiceCommand
import com.rider.voice_commands.droidkaigi_gvp.VoiceCommandExecutor

class RiderToolCallingAdapter(
    private val executor: VoiceCommandExecutor
) {

    fun createToolCall(
        command: VoiceCommand
    ): ToolCall {
        return ToolCall(
            command = command
        )
    }

    fun execute(
        toolCall: ToolCall
    ): ToolCallResult {

        return try {

            val executed = executor.execute(
                toolCall.command
            )

            if (executed) {
                ToolCallResult.Success(
                    command = toolCall.command
                )
            } else {
                ToolCallResult.Failed(
                    command = toolCall.command,
                    message = "Existing command executor failed to execute the command."
                )
            }

        } catch (error: Exception) {

            ToolCallResult.Failed(
                command = toolCall.command,
                message = error.message
                    ?: "Unknown tool execution error."
            )
        }
    }
}
