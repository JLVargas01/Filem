package com.spiralsoft.filem.viewmodel

import android.os.Environment
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope
import java.io.File

class DirectoryExplorerViewModel : ViewModel() {

    private val initialPath: String = Environment.getExternalStorageDirectory().absolutePath // Ruta inicial
    private val pathStack: MutableList<String> = mutableListOf(initialPath) // Pila de rutas
    private val _state: MutableStateFlow<DirectoryExplorerViewState> =
        MutableStateFlow(DirectoryExplorerViewState(currentPath = initialPath)) // Estado de la pantalla
    val state: MutableStateFlow<DirectoryExplorerViewState> get() = _state // Estado publico de la pantalla

    init {
        loadPath(initialPath)
    }

    // Cargar directorios al iniciar la pantalla
    fun initWithPath(initialPath: String) {
        if (pathStack.isEmpty()) {
            pathStack.add(initialPath)
            loadPath(initialPath)
        }
    }

    // Carga de directorios y archivos
    private fun loadPath(path: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(
                isLoading = true,
                currentPath = path
            )

            val dir = File(path)
            val subDirs = dir.listFiles()?.filter {
                it.isDirectory && it.canRead() && !it.isHidden
            }.orEmpty()
            val subFiles = dir.listFiles()?.filter {
                it.isFile && it.canRead() && !it.isHidden
            }.orEmpty()

            _state.value = _state.value.copy(
                directories = subDirs,
                files = subFiles,
                isLoading = false
            )
        }
    }

    // Navegación entre directorios
    fun navigateTo(path: String) {
        pathStack.add(path)
        loadPath(path)
    }

    // Navegación hacia atrás
    fun navigateBack(): Boolean {
        if (pathStack.size <= 1) return false
        pathStack.removeAt(pathStack.lastIndex)
        loadPath(pathStack.last())
        return true
    }

}
