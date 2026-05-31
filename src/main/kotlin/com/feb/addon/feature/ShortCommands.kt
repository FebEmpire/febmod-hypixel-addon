package com.feb.addon.feature

import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.ClientCommands

object ShortCommands {

    private val ShortCommands = listOf(
        "stonks" to "warp stonks",
        "carnival" to "warp carnival",
        "elizabeth" to "warp elizabeth",
        "castle" to "warp castle",
        "da" to "warp da",
        "crypt" to "warp crypt",
        "museum" to "warp museum",
        "wizard" to "warp wizard",
        "dungeon_hub" to "warp dungeon_hub",
        "dungeons" to "warp dungeons",
        "barn" to "warp barn",
        "farming" to "warp farming",
        "desert" to "warp desert",
        "trapper" to "warp trapper",
        "park" to "warp park",
        "jungle" to "warp jungle",
        "howl" to "warp howl",
        "howling_cave" to "warp howling_cave",
        "galatea" to "warp galatea",
        "murkwater" to "warp murkwater",
        "loch" to "warp loch",
        "gold" to "warp gold",
        "deep" to "warp deep",
        "mines" to "warp mines",
        "forge" to "warp forge",
        "crystals" to "warp crystals",
        "hollows" to "warp hollows",
        "nucleus" to "warp nucleus",
        "spider" to "warp spider",
        "top" to "warp top",
        "nest" to "warp nest",
        "arachne" to "warp arachne",
        "end" to "warp end",
        "drag" to "warp drag",
        "void" to "warp void",
        "crimson" to "warp crimson",
        "nether" to "warp nether",
        "isle" to "warp isle",
        "kuudra" to "warp kuudra",
        "skull" to "warp skull",
        "wasteland" to "warp wasteland",
        "dragontail" to "warp dragontail",
        "scarleton" to "warp scarleton",
        "smoldering" to "warp smoldering",
        "garden" to "warp garden",
        "winter" to "warp winter",
        "jerry" to "warp jerry",
        "basecamp" to "warp basecamp",
        "camp" to "warp camp",
        "glacite" to "warp glacite",
        "tunnels" to "warp tunnels",
        "rift" to "warp rift",
        "bayou" to "warp bayou",
        "backwater" to "warp backwater"
    )

    fun register() {
        ClientCommandRegistrationCallback.EVENT.register { dispatcher, _ ->
            ShortCommands.forEach { (shortCmd, fullCmd) ->
                dispatcher.register(
                    ClientCommands.literal(shortCmd)
                        .executes { ctx ->
                            val player = ctx.source.player
                            player.connection.sendCommand(fullCmd)
                            0
                        }
                )
            }
        }
    }
}