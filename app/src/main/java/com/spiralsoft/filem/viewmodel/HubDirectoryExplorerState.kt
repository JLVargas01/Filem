package com.spiralsoft.filem.viewmodel

import java.io.File

data class HubDirectoryExplorerState(
    val files: List<File> = emptyList(), // Lista de archivos disponibles
    val directories: List<File> = emptyList(), // Lista de directorios disponibles
    val isLoading: Boolean = false, //Estado de carga
)
