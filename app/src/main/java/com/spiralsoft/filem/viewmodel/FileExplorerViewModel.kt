package com.spiralsoft.filem.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope
import java.io.File
import android.os.Environment

class FileExplorerViewModel : ViewModel() {

    val state: StateFlow<FileExplorerState> get() = _state
    private val initialPath = Environment.getExternalStorageDirectory().absolutePath
    private val _state = MutableStateFlow(FileExplorerState(currentPath = initialPath))

    init {
        loadDirectory(initialPath)
    }

    fun loadRootDirectories() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isLoading = true)

            val roots = mutableListOf<File>()

            val internalStorage = Environment.getExternalStorageDirectory()

            if (internalStorage.exists() && internalStorage.canRead()) {
                val subDirs = internalStorage.listFiles()?.filter {
                    it.isDirectory && it.canRead() && !it.isHidden
                }.orEmpty()
                roots.addAll(subDirs)
            }

            val externalDirs = File("/storage").listFiles()?.filter {
                it.exists() && it.canRead() && it.isDirectory && it != internalStorage
            }.orEmpty()
            roots.addAll(externalDirs)

            _state.value = _state.value.copy(
                directories = roots,
                isLoading = false
            )
        }
    }

    fun loadDirectory(path: String) {
        viewModelScope.launch(Dispatchers.IO) {
            // Quita el borrado inmediato de los directorios
            _state.value = _state.value.copy(
                isLoading = true,
                currentPath = path
            )

            val dir = File(path)
            val subDirs = dir.listFiles()?.filter {
                it.isDirectory && it.canRead() && !it.isHidden
            }.orEmpty()

            _state.value = _state.value.copy(
                directories = subDirs,
                isLoading = false
            )
        }
    }


}
