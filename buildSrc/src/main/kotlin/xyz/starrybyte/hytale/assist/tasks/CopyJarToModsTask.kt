package xyz.starrybyte.hytale.assist.tasks
import xyz.starrybyte.hytale.assist.utils.getModsDir
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.nio.file.Files
import java.nio.file.StandardCopyOption

abstract class CopyJarToModsTask : DefaultTask() {
    init {
        group = "Hytale"
        description = "Copies the plugin to mods folder"
    }
    @TaskAction
    fun copy() {
        val jar = project.layout.buildDirectory.file("libs/${project.name}-${project.version}.jar")?.get()?.asFile?.toPath()
        if(jar == null){
            println("No jar found")
            return
        }
        val mods = getModsDir(project).toPath()

        val target = mods.resolve(jar.fileName)

        Files.copy(
            jar,
            target,
            StandardCopyOption.REPLACE_EXISTING
        )

        logger.lifecycle("Copied ${jar.fileName} → $mods")
    }
}
