package studio.starrybyte.hytale.examples.input

import com.hypixel.hytale.logger.HytaleLogger
import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.math.vector.Vector3f
import com.hypixel.hytale.protocol.InteractionType
import com.hypixel.hytale.protocol.Packet
import com.hypixel.hytale.protocol.packets.interaction.SyncInteractionChains
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime
import com.hypixel.hytale.server.core.entity.entities.Player
import com.hypixel.hytale.server.core.io.adapter.PlayerPacketWatcher
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.Universe
import com.hypixel.hytale.server.npc.NPCPlugin
import studio.starrybyte.hytale.examples.ui.DynamicPage

class InputExampleListener : PlayerPacketWatcher {
    private val LOGGER = HytaleLogger.forEnclosingClass().atInfo()

    override fun accept(
        var1: PlayerRef?,
        var2: Packet?
    ) {
        val playerRef = var1?:return
        val packet = var2?:return
        if(packet.id != SyncInteractionChains.PACKET_ID)return
        val interactionChains = packet as SyncInteractionChains
        val updates = interactionChains.updates
        for (update in updates) {
            if(update.interactionType == InteractionType.Secondary)
           {
               val pr = playerRef.reference?: return
               val worldId = playerRef.worldUuid ?: return
               val world = Universe.get().getWorld(worldId)?: return
               world.execute {
                   val player = pr.store.getComponent(pr, Player.getComponentType())?:return@execute
                   val page = DynamicPage(playerRef, CustomPageLifetime.CanDismissOrCloseThroughInteraction,"Pages/Test/testp.ui")
                   player.pageManager.openCustomPage(pr, pr.store,page)

                   val result= NPCPlugin.get().spawnNPC(pr.store,"Kweebec_Sapling",null,
                       Vector3d(playerRef.transform.position), Vector3f()
                   )
                   if (result != null) {
                       val npc = result.second()
                       LOGGER.log(npc.npcTypeId)
                   }
               }
           }
        }
    }
}