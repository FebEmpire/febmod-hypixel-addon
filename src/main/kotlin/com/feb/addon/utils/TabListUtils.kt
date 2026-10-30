package com.feb.addon.utils

import net.minecraft.client.Minecraft

object TabListUtils {

    fun getNames(): List<String> {
        val mc = Minecraft.getInstance()
        val connection = mc.connection ?: return emptyList()

        return connection.onlinePlayers.map { playerInfo ->
            playerInfo.tabListDisplayName
                ?.string
                ?: playerInfo.profile.name
        }
    }

    fun getUnformattedNames(): List<String> {
        return getNames().map { name ->
            name.replace("§[0-9a-fk-or]".toRegex(), "").trim()
        }
    }
}