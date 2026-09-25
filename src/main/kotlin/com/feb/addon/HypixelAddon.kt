package com.feb.addon

import com.feb.addon.command.Commands
import com.feb.addon.feature.Features
import com.feb.addon.feature.dungeons.DungeonsRegister
import com.feb.addon.ui.HypixelTab
import com.feb.addon.utils.rotation.RotationTestRunner
import com.feb.addon.utils.rotation.RotationUtils
import com.feb.mod.addon.AddonConfig
import com.feb.mod.addon.AddonContext
import com.feb.mod.addon.FebAddon
import com.feb.mod.ui.gui.FebModGui
import com.feb.mod.ui.gui.tabs.FebTab

class HypixelAddon : FebAddon {
    private lateinit var context: AddonContext

    lateinit var config: AddonConfig<HypixelConfig>

    override fun initialize(context: AddonContext) {
        this.context = context
        config = context.config(HypixelConfig())
        Features.registerAll(config, context)
        DungeonsRegister.registerAll(context)
        RotationUtils.init(context)
        Commands.registerAll(context.commands)
        RotationTestRunner.init(context)
        println("Hypixel addon loaded")
    }

    override fun unload() {
        println("Hypixel addon unloaded")
    }

    override fun createTab(screen: FebModGui): FebTab {
        return HypixelTab()
    }
}