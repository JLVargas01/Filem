/**
 * State para la pantalla principal de explorador de directorios
 */
package com.spiralsoft.filem.presentation.screen.hub

import com.spiralsoft.filem.domain.repository.DirectoryViewState
import java.io.File

data class HubDirectoryExplorerState(
    override val files: List<File> = emptyList(), // Lista de archivos disponibles
    override val directories: List<File> = emptyList(), // Lista de directorios disponibles
    override val isLoading: Boolean = false, //Estado de carga
    override val selectedItems: Set<File> = emptySet() // Lista de elementos seleccionados
) : DirectoryViewState
