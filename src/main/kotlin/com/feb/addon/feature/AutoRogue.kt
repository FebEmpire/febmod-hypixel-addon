package com.feb.addon.feature

import com.feb.addon.utils.InputUtils
import com.feb.addon.utils.ItemUtils
import com.feb.addon.utils.mc
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents

object AutoRogue {

    private const val COOLDOWN_TICKS = 30 * 20

    private val ROGUE_FILTER = ItemUtils.ItemFilter(
        minecraftId = "golden_sword",
        name = "rogue sword",
        lore = "ability: speed boost",
    )

    private enum class Step { IDLE, SWITCHED, USED }

    private var enabled = false
    private var cooldown = 0
    private var returnSlot = -1
    private var step = Step.IDLE
    private var stepTicks = 0

    fun initialize() {
        ClientTickEvents.START_CLIENT_TICK.register { onTick() }
    }

    fun toggle() {
        enabled = !enabled
        if (!enabled) {
            if (step != Step.IDLE && returnSlot >= 0) {
                InputUtils.selectHotbarSlot(returnSlot)
            }
            finishCycle()
        } else {
            cooldown = 0
        }
        com.feb.mod.utils.ChatUtils.modMessage("AutoRogue ${if (enabled) "enabled" else "disabled"}")
    }

    fun isEnabled() = enabled

    private fun onTick() {
        if (!enabled) return
        val player = mc.player
        if (player == null) {
            reset()
            return
        }

        if (step != Step.IDLE) {
            if (mc.screen != null) {
                reset()
                return
            }
            stepTicks--
            when (step) {
                Step.SWITCHED -> if (stepTicks <= 0) {
                    InputUtils.useItem()
                    step = Step.USED
                    stepTicks = 3
                }
                Step.USED -> if (stepTicks <= 0) {
                    if (returnSlot >= 0) InputUtils.selectHotbarSlot(returnSlot)
                    finishCycle()
                }
                Step.IDLE -> {}
            }
            return
        }

        if (cooldown > 0) cooldown--

        if (cooldown <= 0 && mc.screen == null) {
            val found = ItemUtils.findInHotbar(ROGUE_FILTER)
            if (found == null) {
                cooldown = COOLDOWN_TICKS
                return
            }
            if (player.isSpectator || player.isDeadOrDying || player.isUsingItem) {
                cooldown = 20
                return
            }
            returnSlot = player.inventory.selectedSlot
            InputUtils.selectHotbarSlot(found.slot)
            step = Step.SWITCHED
            stepTicks = 2
        }
    }

    private fun finishCycle() {
        returnSlot = -1
        cooldown = COOLDOWN_TICKS
        step = Step.IDLE
        stepTicks = 0
    }

    private fun reset() {
        if (step != Step.IDLE && returnSlot >= 0) {
            InputUtils.selectHotbarSlot(returnSlot)
        }
        finishCycle()
    }

}
