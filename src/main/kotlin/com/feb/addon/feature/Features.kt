package com.feb.addon.feature

import com.feb.addon.HypixelConfig
import com.feb.addon.feature.general.*
import com.feb.addon.feature.solver.*
import com.feb.mod.addon.AddonConfig
import com.feb.mod.addon.AddonContext

object Features {

    fun registerAll(
        config: AddonConfig<HypixelConfig>,
        context: AddonContext
    ) {
        AntiCobbleBreaker.initialize()
        TriggerBot.initialize(config)
        ShortCommands.register()
        AntiLimbo.initialize(context)
        Solver.register()
        AutoRogue.initialize()
    }
}