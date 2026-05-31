package com.feb.addon.feature

import com.feb.addon.utils.RenderUtils
import com.feb.mod.utils.ChatUtils
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderContext
import net.minecraft.client.Minecraft
import net.minecraft.world.entity.animal.axolotl.Axolotl
import net.minecraft.world.entity.monster.Shulker
import java.awt.Color

object EntityESP {
    private var enabled = false
    private val client = Minecraft.getInstance()

    private val axolotlColor = Color(51, 79, 143)
    private val shulkerColor  = Color(51, 79, 143)

    fun initialize() {
        LevelRenderEvents.END_MAIN.register { _: LevelRenderContext ->
            if (!enabled) return@register
            val level = client.level ?: return@register

            for (entity in level.entitiesForRendering()) {
                when (entity) {
                    is Axolotl -> RenderUtils.drawBox(entity.boundingBox, axolotlColor, esp = true)
                    is Shulker -> RenderUtils.drawBox(entity.boundingBox, shulkerColor, esp = true)
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