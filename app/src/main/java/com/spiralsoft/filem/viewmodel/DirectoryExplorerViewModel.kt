package com.spiralsoft.filem.viewmodel

import android.os.Environment
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import java.io.File

class DirectoryExplorerViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val initialPath: String = savedStateHandle["path"]
        ?: Environment.getExternalStorageDirectory().absolutePath // Ruta del directorio actual
    private val pathStack: MutableList<String> = mutableListOf(initialPath) // Pila de rutas
    private val _state: MutableStateFlow<DirectoryExplorerViewState> =
        MutableStateFlow(DirectoryExplorerViewState(currentPath = initialPath)) // Estado de la pantalla
    val state: StateFlow<DirectoryExplorerViewState> get() = _state // Estado publico de la pantalla

    /**
     * Agregar el path a la lista de rutas y cargar el contenido del directorio
     */
    init {
        if (pathStack.isEmpty()) {
            pathStack.add(initialPath)
            loadPath(initialPath)
        }
    }

    /**
     * Carga el contenido del directorio especificado
     * @param path Ruta del directorio para cargar
     */
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

    /**
     * Navega hacia un subdirectorio
     * @param path Ruta del subdirectorio
     */
    fun navigateTo(path: String) {
        pathStack.add(path)
        loadPath(path)
    }

    /**
     * Navega hacia atrás en la pila de rutas
     */
    fun navigateBack(): Boolean {
        if (pathStack.size <= 1) return false
        pathStack.removeAt(pathStack.lastIndex)
        loadPath(pathStack.last())
        return true
    }

}
