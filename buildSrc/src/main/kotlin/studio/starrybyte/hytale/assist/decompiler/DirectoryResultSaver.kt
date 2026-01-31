package studio.starrybyte.hytale.assist.decompiler

import org.jetbrains.java.decompiler.main.extern.IResultSaver
import java.io.File
import java.io.IOException
import java.util.jar.Manifest

class DirectoryResultSaver(
    private val outputDir: File
) : IResultSaver {

    override fun saveFolder(path: String) {
        val folder = File(outputDir, path)
        folder.mkdirs()
    }

    override fun copyFile(source: String, path: String, entryName: String) {
        val target = File(outputDir, "$path/$entryName")
        target.parentFile.mkdirs()
        File(source).copyTo(target, overwrite = true)
    }

    override fun saveClassFile(
        path: String,
        qualifiedName: String,
        entryName: String,
        content: String,
        mapping: IntArray?
    ) {
        val file = File(outputDir, "$path/$entryName")
        file.parentFile.mkdirs()
        file.writeText(content)
    }

    override fun createArchive(path: String?, archiveName: String?, manifest: Manifest?) {
        // No-op
    }


    override fun saveDirEntry(path: String, archiveName: String, entryName: String) {
        // No-op
    }

    override fun copyEntry(source: String, path: String, archiveName: String, entry: String) {
        // No-op
    }

    override fun saveClassEntry(
        path: String,
        archiveName: String,
        qualifiedName: String,
        entryName: String,
        content: String
    ) {
        val file = File(outputDir, "$path/$entryName")
        file.parentFile.mkdirs()
        file.writeText(content)
    }

    override fun saveClassEntry(
        path: String,
        archiveName: String,
        qualifiedName: String,
        entryName: String,
        content: String,
        mapping: IntArray?
    ) {
        saveClassEntry(path, archiveName, qualifiedName, entryName, content)
    }

    override fun closeArchive(path: String, archiveName: String) {
        // No-op
    }

    @Throws(IOException::class)
    override fun close() {
        // No-op
    }
}