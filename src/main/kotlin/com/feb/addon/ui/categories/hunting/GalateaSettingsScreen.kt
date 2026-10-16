
package com.feb.addon.ui.categories.hunting

import com.feb.addon.feature.hunting.GalateaESP
import com.feb.mod.ui.gui.FebModGui
import com.feb.mod.ui.gui.components.FebCheckBox
import com.feb.mod.ui.gui.subscreens.AbstractSubScreen
import net.minecraft.client.gui.GuiGraphicsExtractor

class GalateaSettingsScreen(parent: FebModGui) : AbstractSubScreen(parent) {

    private val checkboxes = mutableListOf<FebCheckBox>()

    private val mobNames = listOf(
        "Axolotls",
        "Frogs",
        "Shulkers",
        "Sea Lumies"
    )

    override fun getTitle() = "Galatea ESP Settings"

    override val contentX: Int
        get() = FebModGui.CONTENT_X_ADDONS

    override fun initializeContent() {
        var y = FebModGui.TOP_BAR_HEIGHT + 52

        fun addMobCheckbox(
            name: String,
            enabled: Boolean,
            onChanged: (Boolean) -> Unit
        ) {
            val labelWidth = parent.font.width(name)

            val checkbox = FebCheckBox(
                contentX + 10 + labelWidth + 8,
                y - 3,
                18,
                enabled,
                onChanged
            )

            checkboxes.add(checkbox)
            addWidget(checkbox)
            y += 26
        }

        addMobCheckbox(
            "Axolotls",
            GalateaESP.isAxolotlsEnabled(),
            GalateaESP::setAxolotlsEnabled
        )

        addMobCheckbox(
            "Frogs",
            GalateaESP.isFrogsEnabled(),
            GalateaESP::setFrogsEnabled
        )

        addMobCheckbox(
            "Shulkers",
            GalateaESP.isShulkersEnabled(),
            GalateaESP::setShulkersEnabled
        )

        addMobCheckbox(
            "Sea Lumies",
            GalateaESP.isSeaPickleEnabled(),
            GalateaESP::setSeaPickleEnabled
        )
    }

    override fun renderContent(
        graphics: GuiGraphicsExtractor,
        mouseX: Int,
        mouseY: Int,
        delta: Float
    ) {
        var y = FebModGui.TOP_BAR_HEIGHT + 52

        mobNames.forEach { name ->
            graphics.text(
                parent.font,
                name,
                contentX + 10,
                y,
                0xFFFFFFFF.toInt(),
                false
            )

            y += 26
        }
    }

    override fun onClose() {
        checkboxes.clear()
    }
}
