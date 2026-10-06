package com.rider.model_selection.droidkaigi_gvp

class ModelCompatibilityChecker {

    fun check(model: ModelInfo): String? {

        if (!model.local) {
            return "Model is not configured as a local model."
        }

        if (!model.enabled) {
            return "Model is disabled."
        }

        if (!model.installed) {
            return "Model is not installed."
        }

        return null
    }
}
