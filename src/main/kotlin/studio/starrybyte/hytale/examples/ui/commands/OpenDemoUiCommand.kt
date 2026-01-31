package studio.starrybyte.hytale.examples.ui.commands

import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime
import com.hypixel.hytale.server.core.command.system.CommandContext
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand
import com.hypixel.hytale.server.core.entity.entities.Player
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.world.World
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import studio.starrybyte.hytale.examples.ui.DynamicPage
import javax.annotation.Nonnull

class OpenDemoUiCommand : AbstractPlayerCommand("opendemoui", "sample command", false) {
    protected override fun execute(
        @Nonnull commandContext: CommandContext,
        @Nonnull store: Store<EntityStore?>,
        @Nonnull ref: Ref<EntityStore?>,
        @Nonnull playerRef: PlayerRef,
        @Nonnull world: World
    ) {
        val player = store.getComponent(ref, Player.getComponentType())?:return
        val page = DynamicPage(playerRef, CustomPageLifetime.CanDismissOrCloseThroughInteraction)
        world.execute {
            player.pageManager.openCustomPage(ref, store, page)
        }
    }
}