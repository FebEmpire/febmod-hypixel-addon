
package com.feb.addon.feature.hunting

import com.feb.addon.config.ESPConfig
import com.feb.mod.addon.AddonContext
import com.feb.mod.api.event.events.RenderFrameEvent
import com.feb.mod.api.render.RenderApi
import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.animal.axolotl.Axolotl
import net.minecraft.world.entity.animal.frog.Frog
import net.minecraft.world.entity.monster.Shulker
import net.minecraft.world.level.block.Blocks

object GalateaESP {
    private val client = Minecraft.getInstance()

    private var enabled = false
    private var axolotlsEnabled = true
    private var frogsEnabled = true
    private var shulkersEnabled = true
    private var seaPickleEnabled = true

    private const val PICKLE_RADIUS = 8

    fun initialize(context: AddonContext) {
        context.events.on<RenderFrameEvent> {
            if (!enabled) return@on

            val level = client.level ?: return@on
            val player = client.player ?: return@on

            for (entity in level.entitiesForRendering()) {
                val shouldRender = when (entity) {
                    is Axolotl -> axolotlsEnabled
                    is Frog -> frogsEnabled
                    is Shulker -> shulkersEnabled
                    else -> false
                }

                if (!shouldRender) continue

                RenderApi.drawBox(
                    entity.boundingBox,
                    ESPConfig.activeColor,
                    esp = true
                )
            }

            if (seaPickleEnabled) {
                val center = BlockPos.containing(player.position())

                for (x in -PICKLE_RADIUS..PICKLE_RADIUS) {
                    for (y in -PICKLE_RADIUS..PICKLE_RADIUS) {
                        for (z in -PICKLE_RADIUS..PICKLE_RADIUS) {
                            val pos = center.offset(x, y, z)
                            val state = level.getBlockState(pos)

                            if (!state.`is`(Blocks.SEA_PICKLE)) continue

                            val box = state.getShape(level, pos)
                                .bounds()
                                .move(
                                    pos.x.toDouble(),
                                    pos.y.toDouble(),
                                    pos.z.toDouble()
                                )

                            RenderApi.drawBox(
                                box,
                                ESPConfig.activeColor,
                                esp = true
                            )
                        }
                    }
                }
            }
        }
    }

    fun toggle() {
        enabled = !enabled
    }

    fun isEnabled() = enabled

    fun isAxolotlsEnabled() = axolotlsEnabled

    fun setAxolotlsEnabled(value: Boolean) {
        axolotlsEnabled = value
    }

    fun isFrogsEnabled() = frogsEnabled

    fun setFrogsEnabled(value: Boolean) {
        frogsEnabled = value
    }

    fun isShulkersEnabled() = shulkersEnabled

    fun setShulkersEnabled(value: Boolean) {
        shulkersEnabled = value
    }

    fun isSeaPickleEnabled() = seaPickleEnabled

    fun setSeaPickleEnabled(value: Boolean) {
        seaPickleEnabled = value
    }
}
