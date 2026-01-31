package studio.starrybyte.hytale.examples.ui

import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime
import com.hypixel.hytale.server.core.entity.entities.player.pages.BasicCustomUIPage
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder
import com.hypixel.hytale.server.core.universe.PlayerRef

class DynamicPage : BasicCustomUIPage {
    constructor(playerRef: PlayerRef, lifetime: CustomPageLifetime) : super(playerRef, lifetime)

    override fun build(var1: UICommandBuilder?) {
        val builder = var1?:return
        builder.append("Pages/Test/test.ui")
        //builder.appendInline("hello","")
        builder.appendInline(
                "#ElementList",
        "Label { Text: %server.customUI.itemRepairPage.noItems; Style: (Alignment: Center); }"
        )
    }

}