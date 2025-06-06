package com.spiralsoft.filem.domain.repository

import java.nio.file.Path

interface DirectoryViewState {
    val files: List<Path> // Lista de archivos disponibles
    val directories: List<Path> // Lista de directorios disponibles
    val isLoading: Boolean // Estado de carga
    val selectedItems: Set<Path> // Lista de elementos seleccionados
}
