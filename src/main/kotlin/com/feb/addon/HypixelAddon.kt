package com.feb.addon

import com.feb.mod.addon.AddonContext
import com.feb.mod.addon.FebAddon
import com.feb.addon.command.Commands
import com.feb.addon.feature.Features
import com.feb.mod.ui.gui.FebModGui
import com.feb.mod.ui.gui.tabs.FebTab
import com.feb.addon.ui.HypixelTab
import com.feb.addon.utils.rotation.RotationTestRunner
import com.feb.addon.utils.rotation.RotationUtils

class HypixelAddon : FebAddon {
    override val name = "FebMod-Hypixel-addon"
    override val version = "0.1.3"

    lateinit var config: com.feb.mod.addon.AddonConfig<HypixelConfig>

    override fun initialize(context: AddonContext) {
        config = context.config(HypixelConfig())
        Features.registerAll(config)
        RotationUtils.init()
        Commands.registerAll()
        RotationTestRunner.init()
        println("Hypixel addon loaded")
    }

    override fun createTab(screen: FebModGui): FebTab {
        return HypixelTab()
    }
}