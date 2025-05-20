package com.spiralsoft.filem.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.spiralsoft.filem.viewmodel.DirectoryExplorerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DirectoryExplorerScreen(
    initialPath: String, // Ruta inicial
    viewModel: DirectoryExplorerViewModel = viewModel() // ViewModel de la pantalla
) {

    val state by viewModel.state.collectAsState() // Estado de la pantalla

    // Se inicializa el ViewModel con la ruta inicial
    LaunchedEffect(Unit) {
        viewModel.initWithPath(initialPath)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Filem - ${state.currentPath}") },
                navigationIcon = {
                    if (state.currentPath != initialPath) {
                        IconButton(onClick = { viewModel.navigateBack() }) {
                            Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Atrás")
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        // Contenido de la pantalla
        DirectoryContent(
            state = state,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            onNavigateTo = { path ->
                viewModel.navigateTo(path)
            }
        )

    }
}
