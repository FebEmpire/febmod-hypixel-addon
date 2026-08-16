package com.feb.addon.ui

import com.feb.addon.feature.AntiCobbleBreaker
import com.feb.addon.feature.AutoRogue
import com.feb.addon.feature.EntityESP
import com.feb.addon.feature.TriggerBot
import com.feb.mod.ui.gui.FebModGui
import com.feb.mod.ui.gui.components.FebButton
import com.feb.mod.ui.gui.tabs.FebTab
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.network.chat.Component

class HypixelTab : FebTab {
    override val displayName = "Hypixel Addon"
    private lateinit var screen: FebModGui
    private val buttons = mutableListOf<FebButton>()

    override fun init(screen: FebModGui) {
        this.screen = screen
        buttons.clear()

        val antiCobbleBtn = FebButton(
            FebModGui.CONTENT_X_ADDONS + 10,
            FebModGui.TOP_BAR_HEIGHT + 10,
            160,
            20,
            Component.literal("Anti Cobble Breaker"),
            screen.font
        ) {
            AntiCobbleBreaker.toggle()
            buttons[0].toggled = AntiCobbleBreaker.isEnabled()
        }
        antiCobbleBtn.toggled = AntiCobbleBreaker.isEnabled()
        buttons.add(antiCobbleBtn)
        screen.addWidget(antiCobbleBtn)

        val triggerBotBtn = FebButton(
            FebModGui.CONTENT_X_ADDONS + 10,
            FebModGui.TOP_BAR_HEIGHT + 36,
            160,
            20,
            Component.literal("Trigger Bot"),
            screen.font
        ) {
            TriggerBot.toggle()
            buttons[1].toggled = TriggerBot.isEnabled()
        }
        triggerBotBtn.toggled = TriggerBot.isEnabled()
        buttons.add(triggerBotBtn)
        screen.addWidget(triggerBotBtn)

        val espBtn = FebButton(
            FebModGui.CONTENT_X_ADDONS + 10,
            FebModGui.TOP_BAR_HEIGHT + 62,
            160,
            20,
            Component.literal("ESP"),
            screen.font,
            onPress = {
                EntityESP.toggle()
                buttons[2].toggled = EntityESP.isEnabled()
            },
            onRightClick = {
                screen.openSubScreen(ESPConfigSubScreen(screen))
            }
        )
        espBtn.toggled = EntityESP.isEnabled()
        buttons.add(espBtn)
        screen.addWidget(espBtn)

        val autoRogueBtn = FebButton(
            FebModGui.CONTENT_X_ADDONS + 10,
            FebModGui.TOP_BAR_HEIGHT + 88,
            160,
            20,
            Component.literal("Auto Rogue"),
            screen.font
        ) {
            AutoRogue.toggle()
            buttons[3].toggled = AutoRogue.isEnabled()
        }
        autoRogueBtn.toggled = AutoRogue.isEnabled()
        buttons.add(autoRogueBtn)
        screen.addWidget(autoRogueBtn)
    }

    override fun render(graphics: GuiGraphicsExtractor, mouseX: Int, mouseY: Int, delta: Float) {
        graphics.text(
            screen.font,
            displayName,
            FebModGui.CONTENT_X_ADDONS + 10,
            FebModGui.TOP_BAR_HEIGHT - 12,
            0xFFFFFFFF.toInt(),
            true
        )
    }

    override fun clear() {
        buttons.forEach { screen.removeWidget(it) }
        buttons.clear()
    }
}