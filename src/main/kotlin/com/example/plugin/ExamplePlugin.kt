package com.example.plugin

import com.hypixel.hytale.logger.HytaleLogger
import com.hypixel.hytale.server.core.plugin.JavaPlugin
import com.hypixel.hytale.server.core.plugin.JavaPluginInit

class ExamplePlugin(javaPluginInit: JavaPluginInit) : JavaPlugin(javaPluginInit) {
    private val LOGGER = HytaleLogger.forEnclosingClass()
    init {
        LOGGER.atInfo()
            .log("Hello from %s version %s", this.name, this.manifest.version.toString())
    }
}