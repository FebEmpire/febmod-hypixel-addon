package com.feb.addon.feature.solver.dwarven

import com.feb.mod.utils.ChatUtils

class FetchurSolver {

    companion object {
        private val REGEX = Regex("""^\[NPC] Fetchur: (?:its|theyre) ([a-zA-Z, \-]*)$""")
        private val ANSWERS = mapOf(
            "yellow and see through" to "Yellow Stained Glass",
            "circular and sometimes moves" to "Compass",
            "expensive minerals" to "Mithril",
            "useful during celebrations" to "Firework Rocket",
            "hot and gives energy" to "Cheap / Decent / Black Coffee",
            "tall and can be opened" to "Any Wooden Door / Iron Door",
            "brown and fluffy" to "Rabbit Foot",
            "explosive but more than usual" to "Superboom TNT",
            "wearable and grows" to "Pumpkin",
            "shiny and makes sparks" to "Flint and Steel",
            "green and some dudes trade stuff for it" to "Emerald",
            "red and soft" to "Red Wool"
        )
    }

    fun onChat(message: String) {
        val match = REGEX.matchEntire(message) ?: return
        val riddle = match.groupValues[1]
        val answer = ANSWERS.getOrDefault(riddle, "Unknown: $riddle")
        ChatUtils.modMessage("Fetchur needs: $answer")
    }
}