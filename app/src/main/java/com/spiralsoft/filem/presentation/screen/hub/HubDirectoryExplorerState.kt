/**
 * State para la pantalla principal de explorador de directorios
 */
package com.spiralsoft.filem.presentation.screen.hub

import java.io.File

data class HubDirectoryExplorerState(
    val files: List<File> = emptyList(), // Lista de archivos disponibles
    val directories: List<File> = emptyList(), // Lista de directorios disponibles
    val isLoading: Boolean = false, //Estado de carga
    val selectedItems: Set<File> = emptySet() // Lista de elementos seleccionados
)
