package com.rider.tool_calling.droidkaigi_gvp

import com.rider.voice_commands.droidkaigi_gvp.VoiceCommand
import com.rider.voice_commands.droidkaigi_gvp.VoiceCommandExecutor

class Feature50ToolCallingAdapter(
    executor: VoiceCommandExecutor
) {

    private val adapter =
        RiderToolCallingAdapter(
            executor = executor
        )

    fun createToolCall(
        command: VoiceCommand
    ): ToolCall {
        return adapter.createToolCall(command)
    }

    fun execute(
        toolCall: ToolCall
    ): ToolCallResult {
        return adapter.execute(toolCall)
    }

    fun executeCommand(
        command: VoiceCommand
    ): ToolCallResult {

        val toolCall =
            adapter.createToolCall(command)

        return adapter.execute(toolCall)
    }
}
