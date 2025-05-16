package com.spiralsoft.filem.viewmodel

import java.io.File

data class FileExplorerState(
    val currentPath: String = android.os.Environment.getExternalStorageDirectory().absolutePath,
    val files: List<File> = emptyList(),
    val sortMode: SortMode = SortMode.BY_NAME,
    val showHiddenFiles: Boolean = false, // Aún no implementado
)
