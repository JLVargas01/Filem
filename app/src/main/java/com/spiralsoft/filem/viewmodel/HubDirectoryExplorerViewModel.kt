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

class HubDirectoryExplorerViewModel : ViewModel() {

    private val _state = MutableStateFlow(HubDirectoryExplorerState()) // Estado de la pantalla privado
    val state: StateFlow<HubDirectoryExplorerState> get() = _state  // Estado de la pantalla público

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
            val internalStorage = Environment.getExternalStorageDirectory()
            if (internalStorage.exists() && internalStorage.canRead()) {
                val subDirs = internalStorage.listFiles()?.filter {
                    it.isDirectory && it.canRead() && !it.isHidden
                }.orEmpty()
                val subFiles = internalStorage.listFiles()?.filter {
                    it.isFile && it.canRead() && !it.isHidden
                }.orEmpty()
                rootDirectories.addAll(subDirs)
                rootFiles.addAll(subFiles)
            }

            // Buscar el contenido del almacenamiento externo
            // NOTA: Aun no se hace nada si es que existe
            val externalDirs = File("/storage").listFiles()?.filter {
                it.exists() && it.canRead() && it.isDirectory && it != internalStorage
            }.orEmpty()

            // Actualizar el estado de la pantalla
            _state.value = _state.value.copy(
                directories = rootDirectories,
                files = rootFiles,
                isLoading = false
            )

        }
    }

}
