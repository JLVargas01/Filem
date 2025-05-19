package com.spiralsoft.filem.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope
import java.io.File
import android.os.Environment

class HubFileExplorerViewModel : ViewModel() {

    private val _state = MutableStateFlow(HubFileExplorerState()) // Estado de la pantalla privado
    val state: StateFlow<HubFileExplorerState> get() = _state  // Estado de la pantalla público

    // Cargar directorios al iniciar la pantalla
    init {
        loadRootDirectories()
        loadRootFiles()
    }

    // Cargar directorios en root
    fun loadRootDirectories() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isLoading = false)

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

    // Cargar archivos en root
    fun loadRootFiles() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isLoading = false)

            val roots = mutableListOf<File>()

            val internalStorage = Environment.getExternalStorageDirectory()

            if (internalStorage.exists() && internalStorage.canRead()) {
                val subFiles = internalStorage.listFiles()?.filter {
                    it.exists() && it.canRead() && it.isFile && it != internalStorage
                }.orEmpty()
                roots.addAll(subFiles)
            }

            val externalDirs = File("/storage").listFiles()?.filter {
                it.exists() && it.canRead() && it.isFile && it != internalStorage
            }.orEmpty()

            roots.addAll(externalDirs)

            _state.value = _state.value.copy(
                files = roots,
                isLoading = false
            )
        }
    }

}
