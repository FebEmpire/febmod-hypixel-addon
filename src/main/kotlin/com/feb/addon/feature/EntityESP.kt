package com.feb.addon.feature

import com.feb.addon.config.ESPConfig
import com.feb.addon.utils.RenderUtils
import com.feb.mod.utils.ChatUtils
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderContext
import net.minecraft.client.Minecraft
import net.minecraft.world.entity.animal.axolotl.Axolotl
import net.minecraft.world.entity.monster.Shulker
import net.minecraft.world.entity.monster.zombie.Zombie
import net.minecraft.world.entity.ambient.Bat
import net.minecraft.world.entity.monster.Silverfish

object EntityESP {
    private var enabled = false
    private val client = Minecraft.getInstance()

    fun initialize() {
        LevelRenderEvents.END_MAIN.register { _: LevelRenderContext ->
            if (!enabled) return@register
            val level = client.level ?: return@register
            for (entity in level.entitiesForRendering()) {
                when (entity) {
                    is Axolotl -> RenderUtils.drawBox(entity.boundingBox, ESPConfig.blue, esp = true)
                    is Shulker -> RenderUtils.drawBox(entity.boundingBox, ESPConfig.febcolor, esp = true)
                    is Zombie -> RenderUtils.drawBox(entity.boundingBox, ESPConfig.green, esp = true)
                    is Silverfish -> RenderUtils.drawBox(entity.boundingBox, ESPConfig.red, esp = true)
                    is Bat -> RenderUtils.drawBox(entity.boundingBox, ESPConfig.febcolor, esp = true)
                }
            }
        }
    }

    fun toggle() {
        enabled = !enabled
        ChatUtils.modMessage("Entity ESP ${if (enabled) "enabled" else "disabled"}")
    }

    fun isEnabled() = enabled
}