package com.feb.addon.feature

import com.feb.mod.utils.ChatUtils
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import net.minecraft.client.Minecraft
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.random.Random

object AntiLimbo {

    private val executor = Executors.newSingleThreadScheduledExecutor()
    private const val LIMBO_MESSAGE = "You were spawned in Limbo."

    fun register() {
        ClientReceiveMessageEvents.GAME.register { message, overlay ->
            if (!overlay) {
                val messageText = message.string
                if (messageText.contains(LIMBO_MESSAGE)) {
                    handleLimboDetected()
                }
            }
        }
    }

    private fun handleLimboDetected() {
        val delayMs = Random.nextLong(3000, 7000)
        val delaySec = delayMs / 1000.0

        ChatUtils.modMessage("Limbo detected: Warping out in %.1f seconds".format(delaySec))

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