package com.spiralsoft.filem.viewmodel

import java.io.File

data class HubFileExplorerState(
    val directories: List<File> = emptyList(),
    val isLoading: Boolean = false,
)
