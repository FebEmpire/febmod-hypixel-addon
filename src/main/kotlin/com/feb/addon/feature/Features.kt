package com.feb.addon.feature

object Features {

    fun registerAll() {
        AntiCobbleBreaker.initialize()
        TriggerBot.initialize()
        EntityESP.initialize()
        ShortCommands.register()
    }

}