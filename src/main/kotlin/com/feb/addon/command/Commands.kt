package com.feb.addon.command

import com.feb.mod.command.CommandRegistry
import com.feb.addon.command.*
import com.feb.addon.feature.AntiCobbleBreaker

object Commands {
    fun registerAll() {
        CommandRegistry.register("cobble") {AntiCobbleBreaker.toggle() }
    }
}