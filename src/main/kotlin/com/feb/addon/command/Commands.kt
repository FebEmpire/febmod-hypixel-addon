package com.feb.addon.command

import com.feb.mod.command.DotCommands
import com.feb.addon.feature.AntiCobbleBreaker
import com.feb.addon.utils.rotation.RotationTestRunner
import com.feb.addon.utils.rotation.RotationUtils

object Commands {
    fun registerAll() {
        DotCommands.register("cobble") { AntiCobbleBreaker.toggle() }

        DotCommands.register("rotate") { args ->
            if (args.isEmpty()) {
                println("Usage: .rotate <yaw> <pitch>  |  .rotate test <count>")
                return@register
            }

            if (args[0] == "test") {
                val count = args.getOrNull(1)?.toIntOrNull()
                if (count == null || count <= 0) {
                    println("Usage: .rotate test <count>")
                    return@register
                }
                RotationTestRunner.start(count)
                return@register
            }

            if (args.size < 2) {
                println("Usage: .rotate <yaw> <pitch>")
                return@register
            }
            val yaw = args[0].toFloatOrNull()
            val pitch = args[1].toFloatOrNull()
            if (yaw == null || pitch == null) {
                println("Invalid yaw/pitch, must be numbers")
                return@register
            }
            RotationUtils.setRotationTarget(yaw, pitch)
        }
    }
}