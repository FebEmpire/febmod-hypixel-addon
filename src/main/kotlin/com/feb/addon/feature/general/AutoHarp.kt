package com.feb.addon.feature.general

import com.feb.mod.addon.AddonContext
import com.feb.mod.api.input.SlotClickApi
import net.minecraft.world.inventory.ContainerInput

object AutoHarp {
    private const val GUI_TITLE_PREFIX = "Harp -"
    private val HARP_SLOTS = 37..43

    private var enabled = false
    private var harpHash = 0
    private var shouldClick = false

    fun initialize(ctx: AddonContext) {
        ctx.gui.onContainerUpdate { container ->
            if (enabled) onContainerUpdate(container)
        }
    }

    fun toggle() {
        enabled = !enabled
        harpHash = 0
        shouldClick = false
    }

    fun isEnabled() = enabled

    private fun onContainerUpdate(
        container: com.feb.mod.api.gui.ContainerView
    ) {
        if (!container.title.startsWith(GUI_TITLE_PREFIX)) {
            harpHash = 0
            shouldClick = false
            return
        }

        var hash0 = 0
        var clickSlot = -1

        for (slot in HARP_SLOTS) {
            val isQuartz =
                container.getStack(slot)?.itemId == "minecraft:quartz_block"

            hash0 = (hash0 shl 1) or if (isQuartz) 1 else 0

            if (clickSlot == -1 && isQuartz) {
                clickSlot = slot
            }
        }

        if (hash0 != harpHash) {
            harpHash = hash0
            shouldClick = clickSlot != -1
        }

        if (shouldClick && clickSlot != -1) {
            if (SlotClickApi.click(clickSlot, 0, ContainerInput.CLONE)) {
                shouldClick = false
            }
        }
    }
}