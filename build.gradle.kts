import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import studio.starrybyte.hytale.assist.tasks.HytaleServerDecompileTask
import studio.starrybyte.hytale.assist.tasks.RunServerTask
import studio.starrybyte.hytale.assist.tasks.CopyJarToModsTask
import studio.starrybyte.hytale.assist.utils.getAssetsPath
import studio.starrybyte.hytale.assist.utils.getServerJarPath

plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    kotlin("jvm") version "2.2.21"
}
val pluginVersion : String by project
val pluginGroup: String by project
group = pluginGroup
version = pluginVersion

repositories {
    mavenCentral()
}


dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
    compileOnly(files(getServerJarPath(project)))
    compileOnly(files(getAssetsPath(project)))

}

sourceSets.register("reference") {
    java.srcDir(layout.buildDirectory.dir("tmp/server_decompiled"))
}

kotlin {
    jvmToolchain(23)
}

tasks.test {
    useJUnitPlatform()
}
tasks.register<HytaleServerDecompileTask>("decompile") {

}

tasks.jar{
    enabled = false
}

tasks.register<RunServerTask>("runServer") {
    val autoDecompileHytaleServer: String by project
    if (autoDecompileHytaleServer.toBoolean()) {
        dependsOn( "decompile", "copyPluginToModsFolder")
    }
    else
        dependsOn( "copyPluginToModsFolder")

}

tasks.register<CopyJarToModsTask>("copyPluginToModsFolder") {
    dependsOn(tasks.named("shadowJar"))
}

tasks.processResources {
    val props = listOf(
        "pluginName",
        "pluginGroup",
        "pluginVersion",
        "pluginDescription",
        "pluginWebsite",
        "hytaleServerVersion",
        "pluginAuthor1",
        "pluginEntrypoint"
    )

    props.forEach { key ->
        inputs.property(key, project.findProperty(key) ?: "")
    }

    filesMatching("**/*.json") {
        expand(props.associateWith { key -> project.findProperty(key) ?: "" })
    }
}

tasks.withType<ShadowJar> {
    archiveBaseName.set(project.name)
    archiveVersion.set(project.version.toString())
    archiveClassifier.set("")
    mergeServiceFiles()
    from(tasks.processResources)
}

// Make build task produce the shadow JAR
tasks.build {
    dependsOn(tasks.shadowJar)
}


