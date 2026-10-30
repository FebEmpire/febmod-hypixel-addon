package com.feb.addon.ui.hud

import com.feb.mod.addon.AddonContext
import com.feb.addon.utils.LocationUtils

object DebugHud {
    fun initialize(context: AddonContext) {
        context.hud.register("debug-hud", "Debug HUD") {
            text { "Addon HUD is working!" }
            text { "Location: ${LocationUtils.location ?: "Unknown"}" }
        }
    }
}