package com.feb.addon.config

object Config {

    fun registerAll() {
        FailsafeConfig.initialize()
    }

}