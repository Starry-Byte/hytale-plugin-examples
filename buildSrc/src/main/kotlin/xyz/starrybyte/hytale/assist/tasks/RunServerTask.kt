package xyz.starrybyte.hytale.assist.tasks
import xyz.starrybyte.hytale.assist.utils.getModsDir
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.JavaExec
import xyz.starrybyte.hytale.assist.utils.getServerJarPath

abstract class RunServerTask : JavaExec() {

    init {
        group = "hytale"
        description = "Runs the Hytale server"
        standardInput = System.`in`
    }

    @TaskAction
    override fun exec() {
        val modsPath = getModsDir(project)

        val jarFile = getServerJarPath(project)
        // Run exactly like: java -jar hytale-server.jar
        workingDir = jarFile.parentFile
        classpath = project.files(jarFile)
        mainClass.set("-jar")
        args(jarFile.absolutePath)
        args("--assets", "../Assets.zip")  // <-- here
        // JVM args from gradle.properties
        val jvmArgss = project.findProperty("hytaleServerJvmArgs")?.toString()
        var jvmArgsString = when {
            jvmArgss != null && jvmArgss !="default" && jvmArgss != "" -> jvmArgss
           else-> "-Xms1G -Xmx8G"
        }
        println("jvmArgs: $jvmArgsString")
        jvmArgsString
            .split("\\s+".toRegex())
            .let { jvmArgs(it) }

        super.exec()
    }
}
