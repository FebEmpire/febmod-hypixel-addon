package com.feb.addon.feature

import com.feb.addon.feature.solver.*

object Features {

    fun registerAll() {
        AntiCobbleBreaker.initialize()
        TriggerBot.initialize()
        EntityESP.initialize()
        ShortCommands.register()
        AntiLimbo.register()
        Solver.register()
    }

}