package com.feb.addon.ui

import com.feb.addon.ui.categories.DungeonsTab
import com.feb.addon.ui.categories.GeneralTab
import com.feb.addon.ui.categories.MiningTab
import com.feb.addon.ui.categories.SettingsTab
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

        addCategory("General", 10) {
            screen.openSubScreen(GeneralTab(screen))
        }

        addCategory("Dungeons", 36) {
            screen.openSubScreen(DungeonsTab(screen))
        }

        addCategory("Mining", 62) {
            screen.openSubScreen(MiningTab(screen))
        }

        addCategory("Settings", 88) {
            screen.openSubScreen(SettingsTab(screen))
        }
    }

    private fun addCategory(
        name: String,
        y: Int,
        onPress: () -> Unit
    ) {
        val button = FebButton(
            FebModGui.CONTENT_X_ADDONS + 10,
            FebModGui.TOP_BAR_HEIGHT + y,
            160,
            20,
            Component.literal(name),
            screen.font,
            onPress = onPress
        )

        buttons.add(button)
        screen.addWidget(button)
    }

    override fun render(
        graphics: GuiGraphicsExtractor,
        mouseX: Int,
        mouseY: Int,
        delta: Float
    ) {
    }

    override fun clear() {
        buttons.forEach { screen.removeWidget(it) }
        buttons.clear()
    }
}