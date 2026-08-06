package com.feb.addon.feature

import com.feb.mod.event.EventBus
import com.feb.mod.event.events.ChatReceivedEvent
import com.feb.mod.utils.ChatUtils
import net.minecraft.client.Minecraft
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.random.Random

object AntiLimbo {

    private val executor = Executors.newSingleThreadScheduledExecutor()
    private const val LIMBO_MESSAGE = "You were spawned in Limbo."
    private var subscription: EventBus.Subscription? = null

    fun register() {
        subscription = EventBus.register<ChatReceivedEvent>("antilimbo") { event ->
            if (event.system && event.message.contains(LIMBO_MESSAGE)) {
                handleLimboDetected()
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
        subscription?.cancel()
    }
}