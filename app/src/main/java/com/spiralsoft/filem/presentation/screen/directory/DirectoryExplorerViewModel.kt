/**
 * ViewModel para la pantalla de explorador de directorios
 */
package com.spiralsoft.filem.presentation.screen.directory

import com.spiralsoft.filem.domain.usecase.LocalManager
import android.os.Environment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import java.nio.file.Path

class DirectoryExplorerViewModel : ViewModel() {

    private val initialPath: Path = Environment.getExternalStorageDirectory().toPath() // Ruta del directorio actual
    private val pathStack: MutableList<Path> = mutableListOf() // Pila de rutas
    private val _state: MutableStateFlow<DirectoryExplorerViewState> =
        MutableStateFlow(DirectoryExplorerViewState(currentPath = initialPath)) // Estado de la pantalla
    val state: StateFlow<DirectoryExplorerViewState> get() = _state // Estado publico de la pantalla
    private val fileManager: LocalManager = LocalManager() // Manager de archivos

    //  Inicializa la pantalla con el directorio especificado
    fun initWithPath(pathDirectory: Path) {
        if (pathStack.isEmpty()) {
            pathStack.add(pathDirectory)
            loadPath(pathDirectory)
        }
    }

    //  Carga el contenido del directorio especificado
    private fun loadPath(pathDirectory: Path) {
        viewModelScope.launch(Dispatchers.IO) {

            _state.value = _state.value.copy(
                isLoading = true,
                currentPath = pathDirectory
            )

            val subDirs: List<Path> = fileManager.getDirectories(pathDirectory) // Lista de directorios de root
            val subFiles: List<Path> = fileManager.getFilesInDirectory(pathDirectory) // LIsta de archivos de root

            _state.value = _state.value.copy(
                directories = subDirs,
                files = subFiles,
                isLoading = false
            )

        }
    }

    //  Navega hacia un subdirectorio
    fun navigateTo(pathDirectory: Path) {
        pathStack.add(pathDirectory)
        loadPath(pathDirectory)
    }

    //  Navega hacia atrás en la pila de rutas
    fun navigateBack(): Boolean {
        if (pathStack.size <= 1) return false
        pathStack.removeAt(pathStack.lastIndex)
        loadPath(pathStack.last())
        return true
    }

    //  Selecciona o deselecciona un elemento
    fun toggleSelection(itemPath: Path) {
        val current = _state.value.selectedItems.toMutableSet()
        if (current.contains(itemPath)) {
            current.remove(itemPath)
        } else {
            current.add(itemPath)
        }
        _state.value = _state.value.copy(selectedItems = current)
    }

}
