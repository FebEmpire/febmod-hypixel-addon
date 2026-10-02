package com.feb.addon.command.failsafe

import com.feb.mod.api.command.CommandApi

object FailsafeCommands {

    fun register(commands: CommandApi) {
        RotationCheckCommand.register(commands)
    }
}