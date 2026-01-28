import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import xyz.starrybyte.hytale.assist.tasks.HytaleServerDecompileTask
import xyz.starrybyte.hytale.assist.tasks.RunServerTask
import xyz.starrybyte.hytale.assist.tasks.CopyJarToModsTask
import xyz.starrybyte.hytale.assist.utils.getServerJarPath

plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    kotlin("jvm") version "2.2.21"
}

group = project.findProperty("pluginGroup") as String
version = project.findProperty("pluginVersion") as String

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
    compileOnly(files(getServerJarPath(project)))
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
    dependsOn( "decompile", "copyPluginToModsFolder")
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