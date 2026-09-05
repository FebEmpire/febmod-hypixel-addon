package com.feb.addon.feature

import com.feb.mod.addon.AddonConfig
import com.feb.addon.HypixelConfig
import net.minecraft.client.Minecraft
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.minecraft.world.phys.EntityHitResult
import net.minecraft.world.phys.HitResult
import com.feb.mod.utils.ChatUtils
import com.feb.addon.utils.InputUtils

object TriggerBot {
    private val client = Minecraft.getInstance()
    private var ticksUntilNextClick = 0
    private lateinit var config: AddonConfig<HypixelConfig>

    private fun randomInterval(): Int {
        val cps = (6..9).random()
        return 20 / cps
    }

    fun initialize(config: AddonConfig<HypixelConfig>) {
        this.config = config

        ClientTickEvents.START_CLIENT_TICK.register {
            if (!config.current.triggerBotEnabled) return@register
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
                InputUtils.attack()
                ticksUntilNextClick = randomInterval()
            } else {
                ticksUntilNextClick--
            }
        }
    }

    fun toggle() {
        config.update { it.triggerBotEnabled = !it.triggerBotEnabled }
        ticksUntilNextClick = 0
        ChatUtils.modMessage("TriggerBot ${if (isEnabled()) "enabled" else "disabled"}")
    }

    fun isEnabled() = config.current.triggerBotEnabled
}