package com.feb.addon.ui.categories

import com.feb.addon.config.FailsafeConfig
import com.feb.mod.ui.gui.FebModGui
import com.feb.mod.ui.gui.subscreens.AbstractSubScreen
import net.minecraft.client.gui.GuiGraphicsExtractor

class FailsafeTab(parent: FebModGui) : AbstractSubScreen(parent) {

    private var selectedSound = 0

    override fun getTitle() = "Failsafes"

    override val contentX: Int
        get() = FebModGui.CONTENT_X_ADDONS

    override fun initializeContent() {
        val sounds = if (FailsafeConfig.sounds.isEmpty()) {
            listOf("No sounds found")
        } else {
            FailsafeConfig.sounds
        }

        createCycleSetting(
            subtitle = "Alert Sound",
            options = sounds,
            getCurrentIndex = {
                selectedSound.coerceIn(0, sounds.lastIndex)
            },
            onValueChange = { index ->
                selectedSound = index
                FailsafeConfig.selectedSound = sounds[index]
            }
        )
    }

    override fun renderContent(
        graphics: GuiGraphicsExtractor,
        mouseX: Int,
        mouseY: Int,
        delta: Float
    ) {
        renderSubtitle(
            graphics,
            "Alert Sound",
            FebModGui.TOP_BAR_HEIGHT + 44
        )
    }
}