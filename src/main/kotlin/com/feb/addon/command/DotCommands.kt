package com.feb.addon.command

import com.feb.addon.feature.AntiCobbleBreaker
import com.feb.addon.utils.RotationUtils
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents

object DotCommands {
    fun initialize() {
        println("DotCommands registering ALLOW_CHAT")
        ClientSendMessageEvents.ALLOW_CHAT.register { message ->
            println("ALLOW_CHAT fired: $message")
            if (message.startsWith(".")) {
                handle(message)
                return@register false
            }
            true
        }
    }

    private fun handle(message: String) {
        val args = message.substring(1).split(" ")
        when (args[0].lowercase()) {
            "cobble" -> AntiCobbleBreaker.toggle()
            "rotate" -> RotationUtils.rotate()
        }
    }
}