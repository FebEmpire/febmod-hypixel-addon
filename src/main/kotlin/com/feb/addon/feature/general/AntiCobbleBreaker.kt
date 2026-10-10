package com.feb.addon.feature.general

import net.minecraft.client.Minecraft
import net.fabricmc.fabric.api.event.player.AttackBlockCallback
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.InteractionResult
import com.feb.mod.api.chat.ModMessage

object AntiCobbleBreaker {
    private var enabled = false
    val client = Minecraft.getInstance()

    fun initialize() {
        AttackBlockCallback.EVENT.register { player, world, hand, pos, direction ->
            if (enabled) {
                val blockState = world.getBlockState(pos)
                if (blockState.block == Blocks.COBBLESTONE) {
                    return@register InteractionResult.FAIL
                }
            }
            InteractionResult.PASS
        }
    }

    fun toggle() {
        enabled = !enabled
        val status = if (enabled) "enabled" else "disabled"
        ModMessage.send("Anti cobble breaker $status")
    }

    fun isEnabled() = enabled
}