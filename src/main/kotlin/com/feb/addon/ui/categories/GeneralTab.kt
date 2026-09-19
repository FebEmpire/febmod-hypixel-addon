package com.feb.addon.ui.categories

import com.feb.addon.feature.general.AntiCobbleBreaker
import com.feb.addon.feature.general.AutoRogue
import com.feb.addon.feature.general.TriggerBot
import com.feb.mod.ui.gui.FebModGui
import com.feb.mod.ui.gui.components.FebButton
import com.feb.mod.ui.gui.subscreens.AbstractSubScreen
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.network.chat.Component

class GeneralTab(parent: FebModGui) : AbstractSubScreen(parent) {

    override fun getTitle() = "General"

    override val contentX: Int
        get() = FebModGui.CONTENT_X_ADDONS

    override fun initializeContent() {
        addToggle(
            "Anti Cobble Breaker",
            44,
            { AntiCobbleBreaker.isEnabled() }
        ) {
            AntiCobbleBreaker.toggle()
        }

        addToggle(
            "Trigger Bot",
            70,
            { TriggerBot.isEnabled() }
        ) {
            TriggerBot.toggle()
        }

        addToggle(
            "Auto Rogue",
            96,
            { AutoRogue.isEnabled() }
        ) {
            AutoRogue.toggle()
        }
    }

    private fun addToggle(
        name: String,
        y: Int,
        getState: () -> Boolean,
        onPress: () -> Unit
    ) {
        lateinit var button: FebButton

        button = FebButton(
            contentX + 10,
            FebModGui.TOP_BAR_HEIGHT + y,
            160,
            20,
            Component.literal(name),
            parent.font
        ) {
            onPress()
            button.toggled = getState()
        }

        button.toggled = getState()
        addWidget(button)
    }

    override fun renderContent(
        graphics: GuiGraphicsExtractor,
        mouseX: Int,
        mouseY: Int,
        delta: Float
    ) {
    }
}