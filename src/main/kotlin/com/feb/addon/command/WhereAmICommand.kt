package com.feb.addon.command

import com.feb.mod.api.command.CommandApi
import com.feb.mod.api.chat.ModMessage
import com.feb.addon.utils.LocationUtils

object WhereAmICommand {

    fun register(commands: CommandApi) {
        commands.register("whereami") {
            execute()
        }
    }

    private fun execute() {
        ModMessage.send("You are in ${LocationUtils.location}")
    }
}