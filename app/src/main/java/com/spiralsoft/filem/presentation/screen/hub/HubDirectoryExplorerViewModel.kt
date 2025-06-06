/**
 * ViewModel para la pantalla principal de explorador de directorios
 */
package com.spiralsoft.filem.presentation.screen.hub

import com.spiralsoft.filem.domain.usecase.LocalManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import android.os.Environment
import java.nio.file.Path

class HubDirectoryExplorerViewModel : ViewModel() {

    private val _state: MutableStateFlow<HubDirectoryExplorerState> =
        MutableStateFlow(HubDirectoryExplorerState()) // Estado de la pantalla privado
    val state: StateFlow<HubDirectoryExplorerState> get() = _state  // Estado de la pantalla público
    private val internalStorage = Environment.getExternalStorageDirectory() // Archivo de tipo 'File' de root
    private val fileManager: LocalManager = LocalManager() // Manager de archivos

    // Cargar directorios al iniciar la pantalla
    init {
        loadPath()
    }

    // Carga de directorios y archivos en root
    private fun loadPath() {
        viewModelScope.launch(Dispatchers.IO) {

            _state.value = _state.value.copy(isLoading = true)

            val rootDirectories: MutableList<Path> = mutableListOf() // Lista de directorios de root
            val rootFiles: MutableList<Path> = mutableListOf() // LIsta de archivos de root

            // Buscar el contenido del almacenamiento interno
            if (internalStorage.exists() && internalStorage.canRead()) {
                rootDirectories.addAll(fileManager.getDirectories(internalStorage.toPath()))
                rootFiles.addAll(fileManager.getFilesInDirectory(internalStorage.toPath()))
            }

            // Actualizar el estado de la pantalla
            _state.value = _state.value.copy(
                directories = rootDirectories,
                files = rootFiles,
                isLoading = false
            )
        }
    }

    // Crear un nuevo directorio en root y recargar el contenido
    fun createDirAndReload(newDirName: String): Boolean {
        if (fileManager.createDirectory(internalStorage.toPath() ,newDirName)) {
            loadPath()
            return true
        }
        return false
    }

    fun deleteSelectedItemsAndReload() {
        val selected: Set<Path> = _state.value.selectedItems
        selected.forEach { fileManager.deleteDirectory(it) }
        clearSelection()
        loadPath()
    }

    fun toggleSelection(itemPath: Path) {
        val current: MutableSet<Path> = _state.value.selectedItems.toMutableSet()
        if (current.contains(itemPath)) {
            current.remove(itemPath)
        } else {
            current.add(itemPath)
        }
        _state.value = _state.value.copy(selectedItems = current)
    }

    private fun clearSelection() {
        _state.value = _state.value.copy(selectedItems = emptySet())
    }

}
