package com.spiralsoft.filem.domain.repository

import java.nio.file.Path

interface FileCrud {

    fun createFile(dirPath: Path, name: String): Boolean // Create
    fun getFilesInDirectory(dirPath: Path): List<Path> // Read
    fun renameFile(filePath: Path, newName: String): Boolean // Update
    fun moveFile(filePath: Path, targetDir: Path): Boolean // Update
    fun copyFile(filePath: Path, targetDir: Path): Boolean // Update
    fun deleteFile(filePath: Path): Boolean // Delete

}
