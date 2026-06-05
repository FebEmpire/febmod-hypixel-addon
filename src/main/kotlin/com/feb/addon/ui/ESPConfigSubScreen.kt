package com.feb.addon.ui

import com.feb.addon.config.ESPConfig
import com.feb.mod.ui.gui.FebModGui
import com.feb.mod.ui.gui.subscreens.AbstractSubScreen
import net.minecraft.client.gui.GuiGraphicsExtractor

class ESPConfigSubScreen(screen: FebModGui) : AbstractSubScreen(screen) {

    override fun getTitle() = "ESP Color"
    override val contentX: Int get() = FebModGui.CONTENT_X_ADDONS

    override fun initializeContent() {
        val colorOptions = listOf(
            "Blue" to ESPConfig.blue,
            "FebColor" to ESPConfig.Februari10Color,
            "Green" to ESPConfig.green,
            "Red" to ESPConfig.red,
            "Th3w4rd3ns" to ESPConfig.th3w4rd3nscolor,
            "Diego" to ESPConfig.diegocolor
        )

        createCycleSetting(
            subtitle = "Select Color",
            options = colorOptions.map { it.first },
            getCurrentIndex = { colorOptions.indexOfFirst { it.second == ESPConfig.activeColor }.coerceAtLeast(0) },
            onValueChange = { index -> ESPConfig.setColor(colorOptions[index].second) }
        )
    }

    override fun renderContent(graphics: GuiGraphicsExtractor, mouseX: Int, mouseY: Int, delta: Float) {
        renderSubtitle(graphics, "Select Color", FebModGui.TOP_BAR_HEIGHT + 44)
    }
}