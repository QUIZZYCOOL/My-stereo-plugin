package com.aliucord.plugins

import android.content.Context
import com.aliucord.annotations.AliucordPlugin
import com.aliucord.entities.Plugin
import com.aliucord.api.CommandsAPI
import com.aliucord.api.CommandsAPI.CommandResult

@AliucordPlugin
class ExamplePlugin : Plugin() {

    override fun load(context: Context) {
        // Force 2-channel stereo instantly on load
        System.setProperty("discord.audio.channels", "2")

        // Creates the chat command: /stereo [on/off]
        commands.registerCommand(
            "stereo",
            "Turn stereo audio on or off",
            listOf(
                CommandsAPI.requiredCommandArg("state", "Type on or off", CommandsAPI.CommandArgumentType.STRING)
            )
        ) { ctx ->
            val state = ctx.getRequiredString("state").lowercase()
            if (state == "on") {
                System.setProperty("discord.audio.channels", "2")
                CommandResult("Stereo audio forced ON! Please rejoin your voice call.", null, false)
            } else if (state == "off") {
                System.setProperty("discord.audio.channels", "1")
                CommandResult("Stereo audio turned OFF. Please rejoin your voice call.", null, false)
            } else {
                CommandResult("Type either '/stereo state:on' or '/stereo state:off'", null, false)
            }
        }
    }

    override fun stop(context: Context) {
        commands.unregisterAll()
        System.setProperty("discord.audio.channels", "1")
    }
}
