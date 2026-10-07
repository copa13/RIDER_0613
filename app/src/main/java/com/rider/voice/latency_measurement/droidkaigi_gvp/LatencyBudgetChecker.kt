package com.rider.voice.latency_measurement.droidkaigi_gvp

class LatencyBudgetChecker(
    private val budget: LatencyBudget
) {

    fun exceeded(
        stage: LatencyStage,
        latencyMs: Long
    ): Boolean {

        val limit = when (stage) {
            LatencyStage.VAD ->
                budget.vadMs

            LatencyStage.STT ->
                budget.sttMs

            LatencyStage.COMMAND_PROCESSING ->
                budget.commandProcessingMs

            LatencyStage.ACTION ->
                budget.actionMs

            LatencyStage.TTS_START ->
                budget.ttsStartMs

            LatencyStage.END_TO_END ->
                budget.endToEndMs
        }

        return limit != null && latencyMs > limit
    }
}
