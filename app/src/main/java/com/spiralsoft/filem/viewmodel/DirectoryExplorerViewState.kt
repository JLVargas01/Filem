/**
 * State para la pantalla de explorador de directorios
 */
package com.spiralsoft.filem.viewmodel

import java.io.File

data class DirectoryExplorerViewState(
    val files: List<File> = emptyList(), // Lista de archivos disponibles
    val directories: List<File> = emptyList(), // Lista de directorios disponibles
    val isLoading: Boolean = false, // Estado de carga
    val selectedItems: Set<File> = emptySet(), // Lista de elementos seleccionados
    val currentPath: String // Ruta actual
)
