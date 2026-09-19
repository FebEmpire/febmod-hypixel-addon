package com.feb.addon.ui.categories

import com.feb.addon.ui.ESPConfigSubScreen
import com.feb.mod.ui.gui.FebModGui
import com.feb.mod.ui.gui.components.FebButton
import com.feb.mod.ui.gui.subscreens.AbstractSubScreen
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.network.chat.Component

class SettingsTab(parent: FebModGui) : AbstractSubScreen(parent) {

    override fun getTitle() = "Settings"

    override val contentX: Int
        get() = FebModGui.CONTENT_X_ADDONS

    override fun initializeContent() {
        val espButton = FebButton(
            contentX + 10,
            FebModGui.TOP_BAR_HEIGHT + 44,
            160,
            20,
            Component.literal("ESP"),
            parent.font
        ) {
            parent.openSubScreen(ESPConfigSubScreen(parent))
        }

        addWidget(espButton)
    }

    override fun renderContent(
        graphics: GuiGraphicsExtractor,
        mouseX: Int,
        mouseY: Int,
        delta: Float
    ) {
    }
}