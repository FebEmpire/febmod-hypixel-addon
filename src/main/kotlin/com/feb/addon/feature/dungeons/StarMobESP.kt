package com.feb.addon.feature.dungeons

import com.feb.addon.config.ESPConfig
import com.feb.mod.api.chat.ModMessage
import com.feb.mod.api.event.Event
import com.feb.mod.api.event.EventBus
import com.feb.mod.api.event.events.RenderFrameEvent
import com.feb.mod.api.render.RenderApi
import com.feb.mod.addon.AddonContext
import net.minecraft.client.Minecraft
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.phys.AABB

object StarMobESP {

    private val client = Minecraft.getInstance()

    private var enabled = false

    fun initialize(context: AddonContext) {
        context.events.on<RenderFrameEvent> {
            if (!enabled) return@on

            val level = client.level ?: return@on
            val livingEntities = level.entitiesForRendering()
                .filterIsInstance<LivingEntity>()
                .filter { it.health > 0f }

            for (marker in level.entitiesForRendering()) {
                if (!marker.displayName.string.contains("✯ ")) continue

                val markerBox = AABB(
                    marker.x - 0.5,
                    marker.y - 2.0,
                    marker.z - 0.5,
                    marker.x + 0.5,
                    marker.y,
                    marker.z + 0.5
                )

                for (entity in livingEntities) {
                    if (entity.boundingBox.intersects(markerBox)) {
                        RenderApi.drawBox(
                            entity.boundingBox,
                            ESPConfig.activeColor,
                            esp = true
                        )
                    }
                }
            }
        }
    }

    fun toggle() {
        enabled = !enabled
        ModMessage.send("Star Mob ESP ${if (enabled) "enabled" else "disabled"}")
    }

    fun isEnabled() = enabled
}