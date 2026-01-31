package studio.starrybyte.hytale.assist.tasks
import studio.starrybyte.hytale.assist.decompiler.DirectoryResultSaver
import studio.starrybyte.hytale.assist.decompiler.logger.GradleFernflowerLogger
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*
import org.jetbrains.java.decompiler.main.Fernflower
import studio.starrybyte.hytale.assist.utils.getServerJarPath
import java.io.File

abstract class  HytaleServerDecompileTask : DefaultTask() {

    init {
        group = "hytale"
        description = "Decompiles Hytale Server"
    }
    @TaskAction
    fun run() {
        val outputDir =project.layout.buildDirectory.dir("/tmp/server_decompiled")
        val file =File(outputDir.get().asFile, "decompiled");
        if(file.exists()) {
            println("Skipping decompile, files already exist")
            return
        }
        val fernflower = Fernflower(
            DirectoryResultSaver(outputDir.get().asFile),
            emptyMap(),
            GradleFernflowerLogger()

        )
        fernflower.addSource(getServerJarPath(project))
        fernflower.decompileContext()
        if(!file.exists()) {
            file.createNewFile()
        }
    }
}
