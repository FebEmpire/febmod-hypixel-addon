package com.feb.addon.feature.solver

import com.feb.addon.feature.solver.dwarven.PuzzlerSolver
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderContext

object Solver {

    private val puzzlerSolver = PuzzlerSolver()

    fun register() {
        ClientReceiveMessageEvents.GAME.register { message, _ ->
            val plain = message.string.replace(Regex("§[0-9a-fk-or]"), "")
            puzzlerSolver.onChat(plain)
        }

        LevelRenderEvents.END_MAIN.register { _: LevelRenderContext ->
            puzzlerSolver.render()
        }
    }

    fun render() {
        puzzlerSolver.render()
    }
}