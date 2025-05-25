/**
 * Pantalla principal
 * solamente muestra el contenido de la carpeta raiz "/"
 */

package com.spiralsoft.filem.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.spiralsoft.filem.viewmodel.HubDirectoryExplorerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HubFileExplorerScreen(
    viewModel: HubDirectoryExplorerViewModel = viewModel(), // ViewModel de la pantalla
    onNavigateToFile: (String) -> Unit // Función para navegar a una ruta
) {

    val state by viewModel.state.collectAsState() // Estado de la pantalla

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Filem - Inicio") })
        }
    ) { innerPadding ->
        // Contenido de la pantalla
        HubContent(
            state = state,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            onNavigateTo = onNavigateToFile
        )
    }
}
