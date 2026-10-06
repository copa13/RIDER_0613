package YOUR_EXISTING_PACKAGE

sealed class DeviceActionResult {

    data class Success(
        val command: String
    ) : DeviceActionResult()

    data class Failed(
        val command: String,
        val reason: String
    ) : DeviceActionResult()

    data class Invalid(
        val reason: String
    ) : DeviceActionResult()
}
