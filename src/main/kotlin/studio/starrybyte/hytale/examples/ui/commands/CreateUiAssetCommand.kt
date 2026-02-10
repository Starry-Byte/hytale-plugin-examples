package studio.starrybyte.hytale.examples.ui.commands

import com.hypixel.hytale.builtin.asseteditor.AssetEditorPlugin
import com.hypixel.hytale.builtin.asseteditor.AssetPath
import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.server.core.command.system.CommandContext
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.world.World
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import java.nio.file.Path

class CreateUiAssetCommand() : AbstractPlayerCommand("createasset", "createasset") {
    private val title = this.withDefaultArg(
        "title",
        "title",
        ArgTypes.STRING,
        "Default Title",
        "Default Title"
    )
    override fun execute(
        commandContext: CommandContext,
        store: Store<EntityStore?>,
        ref: Ref<EntityStore?>,
        playerRef: PlayerRef,
        world: World
    ) {
        commandContext.run {

        }
        val data = $$"""
            $C = "../../Common.ui";

            $C.@PageOverlay {}

            $C.@DecoratedContainer {
              Anchor: (Width: 600, Height: 400);

              #Title {
                Group {
                  $C.@Title {
                    @Text = "$${title.get(commandContext).trim('"')}";
                  }
                }
              }

              #Content {
                Group {
                  LayoutMode: Left;
                  Padding: (Right: 15, Bottom: 5);

                  Label {
                    FlexWeight: 1;
                    Text: %server.customUI.itemRepairPage.item;
                    Style: (RenderBold: true);
                  }

                  Label {
                    Text: %server.customUI.itemRepairPage.durability;
                    Style: (RenderBold: true);
                  }
                }

                Group #ElementList {
                  FlexWeight: 1;
                  LayoutMode: TopScrolling;
                  ScrollbarStyle: $C.@DefaultScrollbarStyle;

                }
              }
            }

            $C.@BackButton {}

        """.trimIndent()
        val assetPath =AssetPath("a:a", Path.of("Common/UI/Custom/Pages/Test/test2.ui"))
        val assetEditorPlugin =AssetEditorPlugin.get()

        val dataSource = assetEditorPlugin.getDataSourceForPath(assetPath)
        val path = dataSource.getFullPathToAssetData(assetPath.path)
        val file = path.toFile()
        file.parentFile.mkdirs()
        file.writeText(data)
    }
}