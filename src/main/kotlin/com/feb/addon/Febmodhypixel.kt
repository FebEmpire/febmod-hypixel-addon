package com.feb.addon

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object Febmodhypixel : ModInitializer {
    private val logger = LoggerFactory.getLogger("febmod-hypixel")

	override fun onInitialize() {
		logger.info("Hello Fabric world!")
	}
}