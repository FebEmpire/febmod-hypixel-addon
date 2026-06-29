package com.feb.addon.feature.solver.dwarven

import net.minecraft.core.BlockPos
import com.feb.addon.utils.RenderUtils
import java.awt.Color

class PuzzlerSolver {

    companion object {
        private val REGEX = Regex("""^\[NPC] Puzzler: ((?:▲|▶|◀|▼){10})$""")
        private const val START_X = 181
        private const val START_Y = 195
        private const val START_Z = 135
    }

    var targetPos: BlockPos? = null
        private set

    fun onChat(message: String) {
        val match = REGEX.matchEntire(message) ?: return

        var x = START_X
        var z = START_Z

        for (direction in match.groupValues[1]) {
            when (direction) {
                '▲' -> z++
                '▼' -> z--
                '◀' -> x++
                '▶' -> x--
            }
        }

        targetPos = BlockPos(x, START_Y, z)
    }

    fun render() {
        val pos = targetPos ?: return
        RenderUtils.drawBlockPos(pos, Color(255, 80, 80))
    }
}