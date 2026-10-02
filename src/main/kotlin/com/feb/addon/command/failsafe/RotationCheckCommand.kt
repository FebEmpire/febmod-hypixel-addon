package com.feb.addon.command.failsafe

import com.feb.addon.config.FailsafeConfig
import com.feb.addon.failsafe.FailsafeSoundPlayer
import com.feb.mod.api.chat.ModMessage
import com.feb.mod.api.command.CommandApi
import net.minecraft.client.Minecraft
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.random.Random

object RotationCheckCommand {

    private val executor = Executors.newSingleThreadScheduledExecutor()

    fun register(commands: CommandApi) {
        commands.register("febguard", "rotationcheck") {
            val player = Minecraft.getInstance().player ?: return@register

            ModMessage.send("Simulating rotation check in 5 seconds")

            executor.schedule({
                try {
                    val currentYaw = player.yRot
                    val currentPitch = player.xRot

                    val yawChange = Random.nextFloat() * 150f + 30f
                    val pitchChange = Random.nextFloat() * 60f - 30f

                    val newYaw =
                        currentYaw + if (Random.nextBoolean()) yawChange else -yawChange

                    val newPitch =
                        (currentPitch + pitchChange).coerceIn(-90f, 90f)

                    Minecraft.getInstance().execute {
                        player.setYRot(newYaw)
                        player.setXRot(newPitch)

                        FailsafeConfig.selectedSound?.let {
                            FailsafeSoundPlayer.play(it)
                        }

                        ModMessage.send(
                            "Rotation check simulated Yaw: ${
                                String.format("%.1f", newYaw)
                            }°, Pitch: ${
                                String.format("%.1f", newPitch)
                            }°"
                        )
                    }
                } catch (e: Exception) {
                    ModMessage.error(
                        "Error simulating rotation check: ${e.message}"
                    )
                }
            }, 5, TimeUnit.SECONDS)
        }
    }
}