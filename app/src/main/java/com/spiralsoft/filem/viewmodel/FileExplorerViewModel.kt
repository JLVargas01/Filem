package com.spiralsoft.filem.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class FileExplorerViewModel : ViewModel() {

    private val _state = MutableStateFlow(FileExplorerState())
    val state: StateFlow<FileExplorerState> get() = _state

    fun loadFiles(path: String) {
        _state.update {
            it.copy(currentPath = path, isLoading = true)
        }

        viewModelScope.launch(Dispatchers.IO) {
            val currentSort = _state.value.sortMode
            val files = getFilesFromPath(path, currentSort)

            withContext(Dispatchers.Main) {
                _state.update {
                    it.copy(files = files, isLoading = false)
                }
            }
        }
    }

    fun goBack() {
        val parent = File(_state.value.currentPath).parentFile
        if (parent?.canRead() == true) {
            loadFiles(parent.absolutePath)
        }
    }

    fun changeSortMode(newMode: SortMode) {
        val currentPath = _state.value.currentPath
        _state.update { it.copy(sortMode = newMode) }
        loadFiles(currentPath)
    }

    private fun getFilesFromPath(path: String, sortMode: SortMode): List<File> {
        val dir = File(path)
        if (!dir.exists() || !dir.isDirectory || !dir.canRead()) return emptyList()

        val allFiles = dir.listFiles()?.filter { it.canRead() } ?: return emptyList()

        return when (sortMode) {
            SortMode.BY_NAME -> allFiles.sortedWith(compareBy<File> { !it.isDirectory }.thenBy { it.name.lowercase() })
            SortMode.BY_DATE -> allFiles.sortedWith(compareBy<File> { !it.isDirectory }.thenByDescending { it.lastModified() })
            SortMode.BY_SIZE -> allFiles.sortedWith(compareBy<File> { !it.isDirectory }.thenByDescending { it.length() })
        }
    }
}
