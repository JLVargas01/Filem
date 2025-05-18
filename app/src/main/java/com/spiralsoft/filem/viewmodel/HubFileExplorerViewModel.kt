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

    private val _state = MutableStateFlow(HubFileExplorerState())
    val state: StateFlow<HubFileExplorerState> get() = _state

    init {
        loadRootDirectories()
    }

    fun loadRootDirectories() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isLoading = true)

            val roots = mutableListOf<File>()

            val internalStorage = Environment.getExternalStorageDirectory()
            if (internalStorage.exists() && internalStorage.canRead()) {
                roots.add(internalStorage)
            }

            // Agrega tarjetas SD si existen
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
}
