package com.feb.addon.feature.mining

import com.feb.addon.config.ESPConfig
import com.feb.mod.api.event.events.RenderFrameEvent
import com.feb.mod.api.render.RenderApi
import com.feb.mod.addon.AddonContext
import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.Blocks

object TitaniumESP {

    private val client = Minecraft.getInstance()
    private val titaniumBlocks = mutableSetOf<BlockPos>()

    private var enabled = false
    private var lastChunkX = Int.MIN_VALUE
    private var lastChunkZ = Int.MIN_VALUE

    fun initialize(context: AddonContext) {
        context.events.on<RenderFrameEvent> {
            if (!enabled) return@on

            val level = client.level ?: return@on
            val player = client.player ?: return@on

            val chunkX = player.blockPosition().x shr 4
            val chunkZ = player.blockPosition().z shr 4

            if (chunkX != lastChunkX || chunkZ != lastChunkZ) {
                scan(level, chunkX, chunkZ)
            }

            for (pos in titaniumBlocks) {
                RenderApi.drawBox(
                    level.getBlockState(pos).getShape(level, pos).bounds().move(pos),
                    ESPConfig.activeColor,
                    esp = true
                )
            }
        }
    }

    private fun scan(
        level: net.minecraft.client.multiplayer.ClientLevel,
        centerChunkX: Int,
        centerChunkZ: Int
    ) {
        titaniumBlocks.clear()

        for (chunkX in centerChunkX - 2..centerChunkX + 2) {
            for (chunkZ in centerChunkZ - 2..centerChunkZ + 2) {
                for (x in 0..15) {
                    for (z in 0..15) {
                        val worldX = (chunkX shl 4) + x

                        for (y in level.minY..level.maxY) {
                            val pos = BlockPos(worldX, y, (chunkZ shl 4) + z)

                            if (level.getBlockState(pos).block == Blocks.POLISHED_DIORITE) {
                                titaniumBlocks.add(pos.immutable())
                            }
                        }
                    }
                }
            }
        }

        lastChunkX = centerChunkX
        lastChunkZ = centerChunkZ
    }

    fun toggle() {
        enabled = !enabled

        if (!enabled) {
            titaniumBlocks.clear()
            lastChunkX = Int.MIN_VALUE
            lastChunkZ = Int.MIN_VALUE
        }
    }

    fun isEnabled() = enabled
}