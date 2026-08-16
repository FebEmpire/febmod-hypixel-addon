package com.feb.addon.feature

import com.feb.addon.HypixelConfig
import com.feb.addon.feature.solver.*
import com.feb.mod.addon.AddonConfig

object Features {

    fun registerAll(config: AddonConfig<HypixelConfig>) {
        AntiCobbleBreaker.initialize()
        TriggerBot.initialize(config)
        EntityESP.initialize()
        ShortCommands.register()
        AntiLimbo.register()
        Solver.register()
        AutoRogue.initialize()
    }

}