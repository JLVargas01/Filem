package com.spiralsoft.filem.domain.repository

import java.io.File

interface FileCrud {

    fun createFile(name: String): Boolean // Create
    fun getFilesInDirectory(directoryPath: File): List<File> // Read
    fun renameFile(file: File, newName: String): Boolean // Update
    fun moveFile(file: File, targetDir: File): Boolean // Update
    fun copyFile(file: File, targetDir: File): Boolean // Update
    fun deleteFile(file: File): Boolean // Delete

}