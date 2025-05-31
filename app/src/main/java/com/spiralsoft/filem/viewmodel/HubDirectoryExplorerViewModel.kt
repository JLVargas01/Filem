/**
 * ViewModel para la pantalla principal de explorador de directorios
 */
package com.spiralsoft.filem.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope
import java.io.File
import android.os.Environment
import com.spiralsoft.filem.LocalManager

class HubDirectoryExplorerViewModel : ViewModel() {

    private val _state = MutableStateFlow(HubDirectoryExplorerState()) // Estado de la pantalla privado
    val state: StateFlow<HubDirectoryExplorerState> get() = _state  // Estado de la pantalla público
    private val internalStorage: File = Environment.getExternalStorageDirectory() // File de root
    private val fileManager: LocalManager = LocalManager(internalStorage) // Manager de archivos

    // Cargar directorios al iniciar la pantalla
    init {
        loadPath()
    }

    // Carga de directorios y archivos en root
    private fun loadPath() {
        viewModelScope.launch(Dispatchers.IO) {

            _state.value = _state.value.copy(isLoading = false)

            val rootDirectories = mutableListOf<File>() // Lista de directorios de root
            val rootFiles = mutableListOf<File>() // LIsta de archivos de root

            // Buscar el contenido del almacenamiento interno
            if (internalStorage.exists() && internalStorage.canRead()) {
                rootDirectories.addAll(fileManager.getDirectories(internalStorage))
                rootFiles.addAll(fileManager.getFilesInDirectory(internalStorage))
            }

            // Actualizar el estado de la pantalla
            _state.value = _state.value.copy(
                directories = rootDirectories,
                files = rootFiles,
                isLoading = false
            )
        }
    }

    fun toggleSelection(file: File) {
        val current = _state.value.selectedItems.toMutableSet()
        if (current.contains(file)) {
            current.remove(file)
        } else {
            current.add(file)
        }
        _state.value = _state.value.copy(selectedItems = current)
    }

    fun clearSelection() {
        _state.value = _state.value.copy(selectedItems = emptySet())
    }

    // Crear un nuevo directorio en root y recargar el contenido
    fun createDirAndReload(newDirName: String): Boolean {
        if (fileManager.createDirectory(newDirName)) {
            loadPath()
            return true
        }
        return false
    }

    fun deleteDirAndReload(dirDelete: File): Boolean {
        if (fileManager.deleteDirectory(dirDelete)) {
            loadPath()
            return true
        }
        return false
    }

    fun deleteSelectedAndReload() {
        val toDelete = _state.value.selectedItems
        toDelete.forEach { fileManager.deleteDirectory(it) } // Solo directorios por ahora
        clearSelection()
        loadPath()
    }

    fun deleteSelectedItems() {
        val selected = _state.value.selectedItems
        selected.forEach { fileManager.deleteDirectory(it) }
        _state.value = _state.value.copy(selectedItems = emptySet())
        loadPath()
    }

}
