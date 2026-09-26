package com.feb.addon.ui.categories

import com.feb.addon.feature.mining.TitaniumESP
import com.feb.mod.ui.gui.FebModGui
import com.feb.mod.ui.gui.components.FebButton
import com.feb.mod.ui.gui.subscreens.AbstractSubScreen
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.network.chat.Component

class MiningTab(parent: FebModGui) : AbstractSubScreen(parent) {

    override fun getTitle() = "Mining"

    override val contentX: Int
        get() = FebModGui.CONTENT_X_ADDONS

    override fun initializeContent() {
        lateinit var titaniumButton: FebButton

        titaniumButton = FebButton(
            contentX + 10,
            FebModGui.TOP_BAR_HEIGHT + 44,
            160,
            20,
            Component.literal("Titanium ESP"),
            parent.font
        ) {
            TitaniumESP.toggle()
            titaniumButton.toggled = TitaniumESP.isEnabled()
        }

        titaniumButton.toggled = TitaniumESP.isEnabled()
        addWidget(titaniumButton)
    }

    override fun renderContent(
        graphics: GuiGraphicsExtractor,
        mouseX: Int,
        mouseY: Int,
        delta: Float
    ) {
    }
}