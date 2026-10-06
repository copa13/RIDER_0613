package com.rider.command_confirmation.droidkaigi_gvp

object Feature49Architecture {

    const val FEATURE_NUMBER = 49

    const val FEATURE_NAME = "Command Confirmation"

    const val PIPELINE =
        "#47/#48 Command -> Confirmation Policy -> Confirmation -> #47 Executor"

    const val OFFLINE = true

    const val EXECUTION_RULE =
        "Confirmation-required commands are never executed before confirmation."

    const val UNKNOWN_CONFIRMATION_RULE =
        "Unknown confirmation input does not execute the pending command."

    const val EXPIRATION_MILLIS = 30_000L
}
