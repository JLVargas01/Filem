package com.spiralsoft.filem.domain.repository

import java.io.File

interface DirectoryViewState {
    val files: List<File> // Lista de archivos disponibles
    val directories: List<File> // Lista de directorios disponibles
    val isLoading: Boolean // Estado de carga
    val selectedItems: Set<File> // Lista de elementos seleccionados
}
