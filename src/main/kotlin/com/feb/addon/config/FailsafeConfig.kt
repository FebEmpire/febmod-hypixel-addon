package com.feb.addon.config

import net.fabricmc.loader.api.FabricLoader
import java.io.File

object FailsafeConfig {

    private val directory: File =
        FabricLoader.getInstance()
            .gameDir
            .resolve("febmod/addons/hypixel/failsafeconfig")
            .toFile()

    var sounds: List<String> = emptyList()
        private set

    var selectedSound: String? = null

    fun initialize() {
        if (!directory.exists()) {
            directory.mkdirs()
        }

        reload()
    }

    fun reload() {
        sounds = directory
            .listFiles()
            ?.filter { file ->
                file.isFile &&
                        (file.extension.equals("mp3", true) ||
                                file.extension.equals("wav", true))
            }
            ?.sortedBy { it.name.lowercase() }
            ?.map { it.name }
            ?: emptyList()

        if (selectedSound !in sounds) {
            selectedSound = sounds.firstOrNull()
        }
    }

    fun getSoundFile(name: String): File? {
        return sounds
            .firstOrNull { it == name }
            ?.let { File(directory, it) }
    }
}