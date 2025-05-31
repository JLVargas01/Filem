package com.spiralsoft.filem

import java.io.File

class LocalManager(private val filePath: File) : FileCrud, DirectoryCrud {

    override fun createDirectory(name: String): Boolean {
        val newDir = File(filePath, name.trim())
        return if (!newDir.exists()) newDir.mkdirs() else false
    }

    override fun getDirectories(directoryPath: File): List<File> {
        return directoryPath.listFiles()
            ?.filter{ it.isDirectory && it.canRead() && !it.isHidden }
            .orEmpty()
    }

    override fun deleteDirectory(dir: File): Boolean {
        return dir.deleteRecursively()
        /* Por si no jala lo de arriba
        if (dirDelete.isDirectory) {
            // Se obtiene el contenido del directorio
            val childrensDiretory: Array<File> = dirDelete.listFiles() ?: return false
            for (child in childrensDiretory) {
                // Se elimina el contenido del directorio recursivamente
                // o si el directorio esta vacio, se elimina
                val isSuccess = if (child.isDirectory) deleteDirectory(child) else child.delete()
                // Si falla, se devuelve false
                if (!isSuccess) return false
            }
        }
        // Se elimina el directorio vacio
        return dirDelete.delete()
         */
    }

    override fun renameDirectory(dir: File, newName: String): Boolean {
        val newDir = File(dir.parentFile, newName.trim())
        return dir.exists() && !newDir.exists() && dir.renameTo(newDir)
    }

    override fun moveDirectory(file: File, targetDir: File): Boolean {
        TODO("Not yet implemented")
    }

    override fun copyDirectory(file: File, targetDir: File): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteFile(file: File): Boolean {
        return file.delete()
    }

    override fun createFile(name: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun getFilesInDirectory(directoryPath: File): List<File> {
        return directoryPath.listFiles()
            ?.filter { it.isFile && it.canRead() && !it.isHidden }
            .orEmpty()
    }

    override fun renameFile(file: File, newName: String): Boolean {
        val newFile = File(file.parentFile, newName.trim())
        return file.exists() && !newFile.exists() && file.renameTo(newFile)
    }

    override fun moveFile(file: File, targetDir: File): Boolean {
        if (!targetDir.exists()) targetDir.mkdirs()
        val destFile = File(targetDir, file.name)
        return file.exists() && file.renameTo(destFile)
    }

    override fun copyFile(file: File, targetDir: File): Boolean {
        return try {
            if (!targetDir.exists()) targetDir.mkdirs()
            val destFile = File(targetDir, file.name)
            file.inputStream().use { input ->
                destFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

}
