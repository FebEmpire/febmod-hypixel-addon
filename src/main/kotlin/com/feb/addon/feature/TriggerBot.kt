package com.feb.addon.feature

import net.minecraft.client.Minecraft
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.minecraft.world.phys.EntityHitResult
import net.minecraft.world.phys.HitResult
import net.minecraft.world.InteractionHand
import com.feb.mod.utils.ChatUtils

object TriggerBot {
    private var enabled = false
    private val client = Minecraft.getInstance()
    private var ticksUntilNextClick = 0

    private fun randomInterval(): Int {
        val cps = (6..9).random()
        return 20 / cps
    }

    fun initialize() {
        ClientTickEvents.START_CLIENT_TICK.register {
            if (!enabled) return@register
            val player = client.player ?: return@register
            val target = client.hitResult ?: return@register

            if (target.type != HitResult.Type.ENTITY) {
                ticksUntilNextClick = 0
                return@register
            }

            val entity = (target as EntityHitResult).entity
            val eyePos = player.getEyePosition(1.0f)
            val entityPos = entity.position()
            val distance = eyePos.distanceTo(entityPos)

            if (distance > 2.8f) {
                ticksUntilNextClick = 0
                return@register
            }

            if (ticksUntilNextClick <= 0) {
                client.gameMode?.attack(player, entity)
                player.swing(InteractionHand.MAIN_HAND)
                ticksUntilNextClick = randomInterval()
            } else {
                ticksUntilNextClick--
            }
        }
    }

    fun toggle() {
        enabled = !enabled
        ticksUntilNextClick = 0
        ChatUtils.modMessage("TriggerBot ${if (enabled) "enabled" else "disabled"}")
    }

    fun isEnabled() = enabled
}