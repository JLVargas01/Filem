package com.spiralsoft.filem.viewmodel

import android.os.Environment
import java.io.File

data class FileExplorerState(
    val currentPath: String = Environment.getExternalStorageDirectory().absolutePath,
    val files: List<File> = emptyList(),
    val sortMode: SortMode = SortMode.BY_NAME,
    val isLoading: Boolean = false,
    val showHiddenFiles: Boolean = false, // Aún no implementado
)
