package com.aliucord.plugins

import android.content.Context
import com.aliucord.annotations.AliucordPlugin
import com.aliucord.entities.Plugin
import com.aliucord.api.CommandsAPI
import com.aliucord.api.CommandsAPI.CommandResult

@AliucordPlugin
class ExamplePlugin : Plugin() {

    override fun load(context: Context) {
        // Automatically default to stereo when loaded
        System.setProperty("discord.audio.channels", "2")

        // Create a chat command: /stereo [on/off]
        commands.registerCommand(
            "stereo",
            "Turn stereo audio on or off",
            listOf(
                CommandsAPI.requiredCommandArg("state", "Type 'on' or 'off'", CommandsAPI.CommandArgumentType.STRING)
            )
        ) { ctx ->
            val state = ctx.getRequiredString("state").lowercase()
            
            if (state == "on") {
                System.setProperty("discord.audio.channels", "2")
                CommandResult("Stereo audio has been forced ON! Please rejoin your voice call.", null, false)
            } else if (state == "off") {
                System.setProperty("discord.audio.channels", "1")
                CommandResult("Stereo audio turned OFF (Mono mode). Please rejoin your voice call.", null, false)
            } else {
                CommandResult("Invalid choice. Please type either '/stereo state:on' or '/stereo state:off'", null, false)
            }
        }
    }

    override fun stop(context: Context) {
        commands.unregisterAll()
        System.setProperty("discord.audio.channels", "1")
    }
}
