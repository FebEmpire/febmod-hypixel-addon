package com.feb.addon.hypixel

import com.feb.mod.Febmod
import com.feb.mod.addon.FebAddon

class HypixelAddon : FebAddon {
    override val name = "FebMod-Hypixel"
    override val version = "1.0.0"

    override fun initialize() {
        println("FebMod-Hypixel addon initalized")
    }
}