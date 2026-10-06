package com.rider.voice_commands.droidkaigi_gvp

object RiderAccessibilityBridge {

    fun goBack(): Boolean =
        RiderAccessibilityService.getInstance()?.goBackAction() == true

    fun goHome(): Boolean =
        RiderAccessibilityService.getInstance()?.goHomeAction() == true

    fun recentApps(): Boolean =
        RiderAccessibilityService.getInstance()?.recentAppsAction() == true

    fun notifications(): Boolean =
        RiderAccessibilityService.getInstance()?.notificationsAction() == true

    fun quickSettings(): Boolean =
        RiderAccessibilityService.getInstance()?.quickSettingsAction() == true

    fun powerMenu(): Boolean =
        RiderAccessibilityService.getInstance()?.powerMenuAction() == true

    fun scrollDown(): Boolean =
        RiderAccessibilityService.getInstance()?.scrollDownAction() == true

    fun scrollUp(): Boolean =
        RiderAccessibilityService.getInstance()?.scrollUpAction() == true

    fun tapConfirm(): Boolean =
        RiderAccessibilityService.getInstance()?.tapText("Confirm") == true

    fun tapCancel(): Boolean =
        RiderAccessibilityService.getInstance()?.tapText("Cancel") == true
}
