package com.spiralsoft.filem.viewmodel

import android.os.Environment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class FileExplorerViewModel : ViewModel() {

    private val _state = MutableStateFlow(FileExplorerState())
    val state: StateFlow<FileExplorerState> get() = _state

    init {
        loadFiles(_state.value.currentPath)
    }

    fun loadFiles(path: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val files = getFilesFromPath(path)
            withContext(Dispatchers.Main) {
                _state.value = _state.value.copy(
                    currentPath = path,
                    files = files
                )
            }
        }
    }

    fun goBack() {
        val parent = File(_state.value.currentPath).parentFile
        if (parent != null && parent.canRead()) {
            loadFiles(parent.absolutePath)
        }
    }

    private fun getFilesFromPath(path: String): List<File> {
        val dir = File(path)
        if (!dir.exists() || !dir.isDirectory || !dir.canRead()) return emptyList()

        val allFiles = dir.listFiles()
            ?.filter { it.exists() && it.canRead() }
            ?: return emptyList()

        return when (_state.value.sortMode) {
            SortMode.BY_NAME -> allFiles.sortedWith(compareBy<File> { !it.isDirectory }.thenBy { it.name.lowercase() })
            SortMode.BY_DATE -> allFiles.sortedWith(compareBy<File> { !it.isDirectory }.thenByDescending { it.lastModified() })
            SortMode.BY_SIZE -> allFiles.sortedWith(compareBy<File> { !it.isDirectory }.thenByDescending { it.length() })
        }
    }

    fun changeSortMode(newMode: SortMode) {
        _state.value = _state.value.copy(sortMode = newMode)
        loadFiles(_state.value.currentPath)
    }
}
