package com.feb.addon.feature.general

import com.feb.mod.addon.AddonContext
import com.feb.mod.api.chat.ModMessage
import net.minecraft.client.Minecraft
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.random.Random

object AntiLimbo {

    private val executor = Executors.newSingleThreadScheduledExecutor()

    fun initialize(ctx: AddonContext) {
        ctx.chat.onReceived { message ->
            if (
                message.isSystem &&
                message.text.contains("You were spawned in Limbo.")
            ) {
                handleLimboDetected()
            }
        }
    }

    private fun handleLimboDetected() {
        val delayMs = Random.nextLong(3000, 7000)
        val delaySec = delayMs / 1000.0

        ModMessage.send(
            "Limbo detected: Warping out in %.1f seconds".format(delaySec)
        )

        executor.schedule({
            val client = Minecraft.getInstance()

            client.execute {
                client.player?.connection?.sendCommand("l")
            }
        }, delayMs, TimeUnit.MILLISECONDS)
    }

    fun shutdown() {
        executor.shutdown()
    }
}