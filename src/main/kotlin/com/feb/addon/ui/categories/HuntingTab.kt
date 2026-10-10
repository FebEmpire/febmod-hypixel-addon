package com.feb.addon.ui.categories

import com.feb.addon.feature.hunting.GalateaESP
import com.feb.addon.ui.categories.hunting.GalateaSettingsScreen
import com.feb.mod.ui.gui.FebModGui
import com.feb.mod.ui.gui.components.FebButton
import com.feb.mod.ui.gui.subscreens.AbstractSubScreen
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.network.chat.Component

class HuntingTab(parent: FebModGui) : AbstractSubScreen(parent) {

    override fun getTitle() = "Hunting"

    override val contentX: Int
        get() = FebModGui.CONTENT_X_ADDONS

    override fun initializeContent() {
        lateinit var galateaButton: FebButton

        galateaButton = FebButton(
            contentX + 10,
            FebModGui.TOP_BAR_HEIGHT + 44,
            160,
            20,
            Component.literal("Galatea ESP"),
            parent.font,
            onRightClick = {
                parent.openSubScreen(GalateaSettingsScreen(parent))
            }
        ) {
            GalateaESP.toggle()
            galateaButton.toggled = GalateaESP.isEnabled()
        }

        galateaButton.toggled = GalateaESP.isEnabled()
        addWidget(galateaButton)
    }

    override fun renderContent(
        graphics: GuiGraphicsExtractor,
        mouseX: Int,
        mouseY: Int,
        delta: Float
    ) {
    }

}
