package studio.starrybyte.hytale.examples

import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.logger.HytaleLogger
import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.math.vector.Vector3f
import com.hypixel.hytale.protocol.InteractionType
import com.hypixel.hytale.protocol.Packet
import com.hypixel.hytale.protocol.packets.interaction.SyncInteractionChains
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime
import com.hypixel.hytale.server.core.command.system.CommandContext
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand
import com.hypixel.hytale.server.core.entity.entities.Player
import com.hypixel.hytale.server.core.entity.entities.player.pages.BasicCustomUIPage
import com.hypixel.hytale.server.core.io.adapter.PacketAdapters
import com.hypixel.hytale.server.core.io.adapter.PacketFilter
import com.hypixel.hytale.server.core.io.adapter.PlayerPacketWatcher
import com.hypixel.hytale.server.core.plugin.JavaPlugin
import com.hypixel.hytale.server.core.plugin.JavaPluginInit
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.Universe
import com.hypixel.hytale.server.core.universe.world.World
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.hypixel.hytale.server.npc.NPCPlugin
import javax.annotation.Nonnull


class ExamplePlugin(javaPluginInit: JavaPluginInit) : JavaPlugin(javaPluginInit) {
    private lateinit var inbound: PacketFilter
    private val LOGGER = HytaleLogger.forEnclosingClass()
    init {
        LOGGER.atInfo()
            .log("Hello from %s version %s", this.name, this.manifest.version.toString())

    }

    override fun setup() {
        super.setup()
        commandRegistry.registerCommand(HelloWorldCommand())
//       world.entityStore.store
//       var player =Universe.get().getPlayerByUsername("starmun", )
//       NPCPlugin.get().spawnNPC()
         inbound = PacketAdapters.registerInbound(SPlayerListener())
    }
    override fun start() {
        super.start()

    }

    override fun shutdown() {
        PacketAdapters.deregisterInbound(inbound)
        super.shutdown()

    }
}
class SPlayerListener : PlayerPacketWatcher{
    private val LOGGER = HytaleLogger.forEnclosingClass().atInfo()

    override fun accept(
        var1: PlayerRef?,
        var2: Packet?
    ) {

        if (
          var2 == null || var1 == null || var2.id != 290) {
            return
         }
        val interactionChains = var2 as SyncInteractionChains
        val updates = interactionChains.updates
        for (update in updates) {
            if(update.interactionType == InteractionType.Secondary)
           {
               val worldId = var1.worldUuid ?: return
               val world = Universe.get().getWorld(worldId)?.entityStore?.store ?: return
               val es = Universe.get().getWorld(worldId)?.entityStore?:return
               val s = es.store
              val ref = Ref(s)
               val pr = var1.reference?: return

               world.externalData.world.execute {
                   val player = s.getComponent(pr, Player.getComponentType())?:return@execute
                   val page: HelloPage =
                       HelloPage(var1, CustomPageLifetime.CanDismissOrCloseThroughInteraction)
                   player.pageManager.openCustomPage(ref, s,page)
                   val result= NPCPlugin.get().spawnNPC(world,"Kweebec_Sapling",null,
                       Vector3d(var1.transform.position) , Vector3f() )
                   if (result != null) {
                       // Successfully spawned
                       val npcRef = result.first();
                       // Retrieve the NPC interface if needed for further interaction
                       val npc = result.second();
                       LOGGER.log(npc.npcTypeId)
                   }
               }

           }

        }
    }

}
class HelloPage : BasicCustomUIPage {
    constructor(playerRef: PlayerRef, lifetime: CustomPageLifetime) : super(playerRef, lifetime)

    override fun build(var1: UICommandBuilder?) {
        val builder = var1?:return
        builder.append("Pages/Test/test.ui")
        //builder.appendInline("hello","")
        builder.appendInline(
                "#ElementList",
        "Label { Text: %server.customUI.itemRepairPage.noItems; Style: (Alignment: Center); }"
        )
        builder.appendInline(
            "#ElementList",
            "Label { Text: %server.customUI.itemRepairPage.noItems; Style: (Alignment: Center); }"
        )
        builder.appendInline(
            "#ElementList",
            "Label { Text: %server.customUI.itemRepairPage.noItems; Style: (Alignment: Center); }"
        )
        builder.appendInline(
            "#ElementList",
            "Label { Text: %server.customUI.itemRepairPage.noItems; Style: (Alignment: Center); }"
        )
        builder.appendInline(
            "#ElementList",
            "Label { Text: %server.customUI.itemRepairPage.noItems; Style: (Alignment: Center); }"
        )
        builder.appendInline(
            "#ElementList",
            "Label { Text: %server.customUI.itemRepairPage.noItems; Style: (Alignment: Center); }"
        )
        builder.appendInline(
            "#ElementList",
            "Label { Text: %server.customUI.itemRepairPage.noItems; Style: (Alignment: Center); }"
        )
        builder.appendInline(
            "#ElementList",
            "Label { Text: %server.customUI.itemRepairPage.noItems; Style: (Alignment: Center); }"
        )
        builder.appendInline(
            "#ElementList",
            "Label { Text: %server.customUI.itemRepairPage.noItems; Style: (Alignment: Center); }"
        )
        builder.appendInline(
            "#ElementList",
            "Label { Text: %server.customUI.itemRepairPage.noItems; Style: (Alignment: Center); }"
        )
        builder.appendInline(
            "#ElementList",
            "Label { Text: %server.customUI.itemRepairPage.noItems; Style: (Alignment: Center); }"
        )
        builder.appendInline(
            "#ElementList",
            "Label { Text: %server.customUI.itemRepairPage.noItems; Style: (Alignment: Center); }"
        )
        builder.appendInline(
            "#ElementList",
            "Label { Text: %server.customUI.itemRepairPage.noItems; Style: (Alignment: Center); }"
        )
        builder.appendInline(
            "#ElementList",
            "Label { Text: %server.customUI.itemRepairPage.noItems; Style: (Alignment: Center); }"
        )
        builder.appendInline(
            "#ElementList",
            "Label { Text: %server.customUI.itemRepairPage.noItems; Style: (Alignment: Center); }"
        )
        builder.appendInline(
            "#ElementList",
            "Label { Text: %server.customUI.itemRepairPage.noItems; Style: (Alignment: Center); }"
        )
        builder.appendInline(
            "#ElementList",
            "Label { Text: %server.customUI.itemRepairPage.noItems; Style: (Alignment: Center); }"
        )
        builder.appendInline(
            "#ElementList",
            "Label { Text: %server.customUI.itemRepairPage.noItems; Style: (Alignment: Center); }"
        )
        builder.appendInline(
            "#ElementList",
            "Label { Text: %server.customUI.itemRepairPage.noItems; Style: (Alignment: Center); }"
        )
        builder.appendInline(
            "#ElementList",
            "Label { Text: %server.customUI.itemRepairPage.noItems; Style: (Alignment: Center); }"
        )
        builder.appendInline(
            "#ElementList",
            "Label { Text: %server.customUI.itemRepairPage.noItems; Style: (Alignment: Center); }"
        )
        builder.appendInline(
            "#ElementList",
            "Label { Text: %server.customUI.itemRepairPage.noItems; Style: (Alignment: Center); }"
        )
        builder.appendInline(
            "#ElementList",
            "Label { Text: %server.customUI.itemRepairPage.noItems; Style: (Alignment: Center); }"
        )
        builder.appendInline(
            "#ElementList",
            "Label { Text: %server.customUI.itemRepairPage.noItems; Style: (Alignment: Center); }"
        )
        builder.appendInline(
            "#ElementList",
            "Label { Text: %server.customUI.itemRepairPage.noItems; Style: (Alignment: Center); }"
        )
        builder.appendInline(
            "#ElementList",
            "Label { Text: %server.customUI.itemRepairPage.noItems; Style: (Alignment: Center); }"
        )
        builder.appendInline(
            "#ElementList",
            "Label { Text: %server.customUI.itemRepairPage.noItems; Style: (Alignment: Center); }"
        )
        builder.appendInline(
            "#ElementList",
            "Label { Text: %server.customUI.itemRepairPage.noItems; Style: (Alignment: Center); }"
        )


    }

}
class HelloWorldCommand : AbstractPlayerCommand("helloworld", "sample command", false) {
    protected override fun execute(
        @Nonnull commandContext: CommandContext,
        @Nonnull store: Store<EntityStore?>,
        @Nonnull ref: Ref<EntityStore?>,
        @Nonnull playerRef: PlayerRef,
        @Nonnull world: World
    ) {
        val player = store.getComponent(ref, Player.getComponentType())?:return
        val page: HelloPage =
            HelloPage(playerRef, CustomPageLifetime.CanDismissOrCloseThroughInteraction)
        checkNotNull(player)
        world.execute {

        player.getPageManager().openCustomPage(ref, store, page)
        }
    }
}