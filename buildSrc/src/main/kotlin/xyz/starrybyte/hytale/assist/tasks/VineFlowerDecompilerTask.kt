package xyz.starrybyte.hytale.assist.tasks
import xyz.starrybyte.hytale.assist.utils.getServerDir
import xyz.starrybyte.hytale.assist.decompiler.DirectoryResultSaver
import xyz.starrybyte.hytale.assist.decompiler.logger.GradleFernflowerLogger
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*
import org.jetbrains.java.decompiler.main.Fernflower

abstract class  HytaleServerDecompileTask : DefaultTask() {

    init {
        group = "hytale"
        description = "Decompiles Hytale Server"
    }
    @TaskAction
    fun run() {
        val outputDir =project.layout.buildDirectory.dir("/tmp/server_decompiled")
        val fernflower = Fernflower(
            DirectoryResultSaver(outputDir.get().asFile),
            emptyMap(),
            GradleFernflowerLogger()

        )
        fernflower.addSource(getServerDir(project))
        fernflower.decompileContext()
    }
}
