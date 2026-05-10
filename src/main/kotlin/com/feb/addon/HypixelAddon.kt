package com.feb.addon

import com.feb.mod.addon.FebAddon
import com.feb.addon.command.Commands
import com.feb.addon.feature.Features
import com.feb.mod.ui.gui.FebModGui
import com.feb.mod.ui.gui.tabs.FebTab
import com.feb.addon.ui.HypixelTab

class HypixelAddon : FebAddon {
    override val name = "FebMod-Hypixel"
    override val version = "0.1.0"

    override fun initialize() {
        Features.registerAll()
        Commands.registerAll()
        println("FebMod-Hypixel initialized")
    }

    override fun createTab(screen: FebModGui): FebTab {
        return HypixelTab()
    }
}