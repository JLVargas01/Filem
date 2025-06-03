package com.spiralsoft.filem.domain.repository

import java.io.File

interface DirectoryCrud {

    fun createDirectory(name: String): Boolean // Create
    fun getDirectories(directoryPath: File): List<File> // Read
    fun renameDirectory(dir: File, newName: String): Boolean // Update
    fun moveDirectory(file: File, targetDir: File): Boolean // Update
    fun copyDirectory(file: File, targetDir: File): Boolean // Update
    fun deleteDirectory(dir: File): Boolean // Delete

}