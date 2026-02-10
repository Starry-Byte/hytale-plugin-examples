package studio.starrybyte.hytale.examples

import com.hypixel.hytale.logger.HytaleLogger
import com.hypixel.hytale.server.core.io.adapter.PacketAdapters
import com.hypixel.hytale.server.core.io.adapter.PacketFilter
import com.hypixel.hytale.server.core.plugin.JavaPlugin
import com.hypixel.hytale.server.core.plugin.JavaPluginInit
import studio.starrybyte.hytale.examples.input.InputExampleListener
import studio.starrybyte.hytale.examples.ui.commands.CreateUiAssetCommand
import studio.starrybyte.hytale.examples.ui.commands.OpenDemoUiCommand

class ExamplesPlugin(javaPluginInit: JavaPluginInit) : JavaPlugin(javaPluginInit) {
    private lateinit var inbound: PacketFilter
    private val LOGGER = HytaleLogger.forEnclosingClass()
    init {
        LOGGER.atInfo()
            .log("Hello from %s version %s", this.name, this.manifest.version.toString())
    }

    override fun setup() {
        super.setup()
        commandRegistry.registerCommand(OpenDemoUiCommand())
        commandRegistry.registerCommand(CreateUiAssetCommand())
         inbound = PacketAdapters.registerInbound(InputExampleListener())
    }

    override fun shutdown() {
        PacketAdapters.deregisterInbound(inbound)
        super.shutdown()
    }
}
