package com.feb.addon.utils

import net.minecraft.client.Minecraft

object LocationUtils {

    val location: String?
        get() {
            for (line in TabListUtils.getUnformattedNames()) {
                if (line.startsWith("Area: ") || line.startsWith("Dungeon: ")) {
                    return convertToLocation(line.substringAfter(": ").trim())
                }
            }

            return null
        }

    private fun convertToLocation(name: String): String? {
        return when (name) {
            "Private Island" -> "Private Island"
            "Hub" -> "Hub"
            "Dungeon Hub" -> "Dungeon Hub"
            "The Farming Islands" -> {
                val player = Minecraft.getInstance().player ?: return null

                if (player.z > -320) "The Barn" else "Mushroom Desert"
            }
            "The Park" -> "The Park"
            "Galatea" -> "Galatea"
            "Gold Mine" -> "Gold Mine"
            "Deep Caverns" -> "Deep Caverns"
            "Dwarven Mines" -> "Dwarven Mines"
            "Crystal Hollows" -> "Crystal Hollows"
            "Spider's Den" -> "Spider's Den"
            "The End" -> "The End"
            "Crimson Isle" -> "Crimson Isle"
            "The Garden" -> "Garden"
            "Backwater Bayou" -> "Backwater Bayou"
            "Jerry's Workshop" -> "Jerry's Workshop"
            "Catacombs" -> "Dungeons"
            "Kuudra" -> "Kuudra"
            "Mineshaft" -> "Mineshaft"
            "The Rift" -> "The Rift"
            else -> null
        }
    }
}