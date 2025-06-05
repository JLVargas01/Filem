package com.spiralsoft.filem.domain.repository

import java.nio.file.Path

interface DirectoryCrud {

    fun createDirectory(dirPath: Path, nameDir: String): Boolean // Create
    fun getDirectories(dirPath: Path): List<Path> // Read
    fun renameDirectory(dirPath: Path, newNameDir: String): Boolean // Update
    fun moveDirectory(dirPath: Path, targetDir: Path): Boolean // Update
    fun copyDirectory(dirPath: Path, targetDir: Path): Boolean // Update
    fun deleteDirectory(dirPath: Path): Boolean // Delete

}
