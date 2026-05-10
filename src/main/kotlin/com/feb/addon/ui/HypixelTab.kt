package com.feb.addon.ui

import com.feb.addon.feature.AntiCobbleBreaker
import com.feb.mod.ui.gui.FebModGui
import com.feb.mod.ui.gui.components.FebButton
import com.feb.mod.ui.gui.tabs.FebTab
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.network.chat.Component

class HypixelTab : FebTab {

    override val displayName = "Hypixel"
    private lateinit var screen: FebModGui
    private val buttons = mutableListOf<FebButton>()

    override fun init(screen: FebModGui) {
        this.screen = screen
        buttons.clear()

        val toggleBtn = FebButton(
            140, 50, 160, 20,
            Component.literal("Anti Cobble Breaker"),
            screen.font
        ) {
            AntiCobbleBreaker.toggle()
            buttons.firstOrNull()?.toggled = AntiCobbleBreaker.isEnabled()
        }
        toggleBtn.toggled = AntiCobbleBreaker.isEnabled()
        buttons.add(toggleBtn)
        screen.addWidget(toggleBtn)
    }

    override fun render(graphics: GuiGraphicsExtractor, mouseX: Int, mouseY: Int, delta: Float) {}

    override fun clear() {
        buttons.forEach { screen.removeWidget(it) }
        buttons.clear()
    }
}