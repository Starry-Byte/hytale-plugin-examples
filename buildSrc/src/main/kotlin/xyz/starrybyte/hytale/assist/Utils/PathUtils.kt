package xyz.starrybyte.hytale.assist.utils

import org.gradle.api.Project
import java.io.File

fun getGameDir(project: Project): File {

   return getGameDir(project.findProperty("hytaleInstallDir")?.toString())
}
fun getGameDir(hytaleInstallDir: String?):File{
    val os = System.getProperty("os.name").lowercase()
    val installDir = when {
        hytaleInstallDir != null && hytaleInstallDir!="default" && hytaleInstallDir!="" -> hytaleInstallDir
        os.contains("win") -> { // Windows
            System.getenv("APPDATA")?.let { "$it/Hytale" }
                ?: error("missingInstallDirError")
        }
        os.contains("mac") -> { // macOS
            "${System.getProperty("user.home")}/Library/Application Support/Hytale"
        }
        else -> { // Linux/Unix
            "${System.getProperty("user.home")}/.hytale"
        }
    }
    val missingInstallDirError ="Please set 'hytaleInstallDir' in gradle.properties \nGame directory does not exist"

    val gameDir = File(installDir)
    require(gameDir.exists()) { "$missingInstallDirError: $gameDir" }
    return gameDir
}
fun getModsDir(project: Project): File {
    return File(getGameDir(project),
        "install/release/package/game/latest/Server/mods")
}
fun getServerDir(project: Project): File {

    val hytaleServerJarDir = project.findProperty("hytaleServerJarDir")?.toString()

    val serverFile = when {
        hytaleServerJarDir != null && hytaleServerJarDir!="default" && hytaleServerJarDir!="" -> {
            File(hytaleServerJarDir)
        }
        else -> {
            File(getGameDir(project),"install/release/package/game/latest/Server/HytaleServer.jar")
        }
    }
    // Server JAR inside the installation directory
    require(serverFile.exists()) { "Please set 'hytaleInstallDir' in gradle.propertiesServer JAR does not exist: $serverFile" }
    return serverFile
}
