package com.feb.addon.ui.categories

import com.feb.addon.feature.dungeons.StarMobESP
import com.feb.mod.ui.gui.FebModGui
import com.feb.mod.ui.gui.components.FebButton
import com.feb.mod.ui.gui.subscreens.AbstractSubScreen
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.network.chat.Component

class DungeonsTab(parent: FebModGui) : AbstractSubScreen(parent) {

    override fun getTitle() = "Dungeons"

    override val contentX: Int
        get() = FebModGui.CONTENT_X_ADDONS

    override fun initializeContent() {
        lateinit var starMobButton: FebButton

        starMobButton = FebButton(
            contentX + 10,
            FebModGui.TOP_BAR_HEIGHT + 44,
            160,
            20,
            Component.literal("Star Mob ESP"),
            parent.font
        ) {
            StarMobESP.toggle()
            starMobButton.toggled = StarMobESP.isEnabled()
        }

        starMobButton.toggled = StarMobESP.isEnabled()
        addWidget(starMobButton)
    }

    override fun renderContent(
        graphics: GuiGraphicsExtractor,
        mouseX: Int,
        mouseY: Int,
        delta: Float
    ) {
    }
}