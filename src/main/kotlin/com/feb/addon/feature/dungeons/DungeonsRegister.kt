package com.feb.addon.feature.dungeons

import com.feb.mod.addon.AddonContext

object DungeonsRegister {

    fun registerAll(context: AddonContext) {
        StarMobESP.initialize(context)
    }
}