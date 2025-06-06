/**
 * Logica para manipular directorios y archivos
 * Esencialmente, se manejan las operaciones CRUD (Create, Read, Update, Delete)
 * para directorios y archivos.
 */
package com.spiralsoft.filem.domain.usecase

import com.spiralsoft.filem.domain.repository.DirectoryCrud
import com.spiralsoft.filem.domain.repository.FileCrud
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.util.stream.Collectors

class LocalManager : FileCrud, DirectoryCrud {

    // Crear un nuevo directorio tomando como base un directorio
    override fun createDirectory(dirPath: Path, nameDir: String): Boolean {
        return try {
            Files.createDirectory(dirPath.resolve(nameDir))
            true
        } catch (e: IOException) {
            false
        }
    }

    // Obtener los directorios en un directorio
    override fun getDirectories(dirPath: Path): List<Path> {
        return try {
            Files.list(dirPath).use { it ->
                it.filter { Files.isDirectory(it) }
                    .collect(Collectors.toList())
            }
        } catch (e: IOException) {
            emptyList()
        }
    }

    // Renombrar el nombre de un directorio
    override fun renameDirectory(dirPath: Path, newNameDir: String): Boolean {
        return try {
            val target: Path = dirPath.parent.resolve(newNameDir)
            if (Files.exists(target)) return false
            Files.move(dirPath, target)
            true
        } catch (e: IOException) {
            false
        }
    }

    // Mover un directorio a un nuevo path
    override fun moveDirectory(dirPath: Path, targetDir: Path): Boolean {
        if (!Files.isDirectory(dirPath) && !Files.isDirectory(targetDir)) return false // Ambos son directorios
        if (targetDir.startsWith(dirPath)) return false // No mover a si mismo
        return try {
            val target: Path = Files.move(dirPath, targetDir.resolve(dirPath.fileName))
            if (Files.exists(target)) return false
            true
        } catch (e: IOException) {
            false
        }
    }

    // Copiar un directorio y su contenido a un nuevo path
    override fun copyDirectory(dirPath: Path, targetDir: Path): Boolean {
        if (!Files.isDirectory(dirPath) && !Files.isDirectory(targetDir)) return false // Ambos son directorios
        if (targetDir.startsWith(dirPath)) return false // No copiar a si mismo
        return try {
            Files.walk(dirPath).use { stream ->
                stream.forEach { source ->
                    val target = targetDir.resolve(dirPath.relativize(source))
                    if (Files.isDirectory(source)) {
                        Files.createDirectories(target)
                    } else {
                        Files.copy(source, target, StandardCopyOption.COPY_ATTRIBUTES
                        )
                    }
                }
            }
            true
        } catch (e: IOException) {
            false
        }
    }

    // Eliminar un directorio y tod0 su contenido
    override fun deleteDirectory(dirPath: Path): Boolean {
        if (!Files.exists(dirPath)) return false // Directorio no existe
        if (!Files.isDirectory(dirPath)) return false // No es un directorio
        return try {
            Files.walk(dirPath)
                .sorted(Comparator.reverseOrder())
                .forEach { path -> Files.delete(path) }
            true
        } catch (e: IOException) {
            false
        }
    }

    // ¿Crear archivos? :/
    override fun createFile(dirPath: Path, name: String): Boolean {
        // No se pueden crear archivos,
        // pero dejo el metodo por si lo llegará a usar
        return false
    }

    // Obtener los archivos en un directorio
    override fun getFilesInDirectory(dirPath: Path): List<Path> {
        return try {
            Files.list(dirPath).use { stream ->
                stream.filter { path -> Files.isRegularFile(path) }
                    .collect(Collectors.toList())
            }
        } catch (e: IOException) {
            emptyList()
        }
    }

    // Renombrar archivo
    override fun renameFile(filePath: Path, newName: String): Boolean {
        return try {
            val newFilePath = filePath.parent.resolve(newName)
            Files.move(filePath, newFilePath)
            true
        } catch (e: IOException) {
            false
        }
    }

    // Mover archivo a un nuevo path
    override fun moveFile(filePath: Path, targetDir: Path): Boolean {
        return try {
            val targetPath = targetDir.resolve(filePath.fileName)
            Files.move(filePath, targetPath)
            true
        } catch (e: IOException) {
            false
        }
    }

    // Copiar archivo a un nuevo path
    override fun copyFile(filePath: Path, targetDir: Path): Boolean {
        return try {
            Files.copy(filePath, targetDir.resolve(filePath.fileName), StandardCopyOption.REPLACE_EXISTING)
            true
        } catch (e: IOException) {
            false
        }
    }

    // Eliminar archivo
    override fun deleteFile(filePath: Path): Boolean {
        return try {
            Files.delete(filePath)
            true
        } catch (e: IOException) {
            false
        }
    }

}
