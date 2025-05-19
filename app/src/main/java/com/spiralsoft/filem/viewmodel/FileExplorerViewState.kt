package com.spiralsoft.filem.viewmodel

import java.io.File

data class FileExplorerState(
    val directories: List<File> = emptyList(),
    val isLoading: Boolean = false,
    val currentPath: String
)
