/**
 * Pantalla principal
 * solamente muestra el contenido de la carpeta raiz "/"
 */

package com.spiralsoft.filem.presentation.screen.hub

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.spiralsoft.filem.presentation.components.BottomActionBar
import com.spiralsoft.filem.presentation.components.ConfirmDeleteDialog
import com.spiralsoft.filem.presentation.components.CreateDirectoryDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HubFileExplorerScreen(
    viewModel: HubDirectoryExplorerViewModel = viewModel(), // ViewModel de la pantalla
    onNavigateToFile: (String) -> Unit // Función para navegar a una ruta
) {

    val state by viewModel.state.collectAsState() // Estado de la pantalla
    var showDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        ConfirmDeleteDialog(
            onConfirm = {
                viewModel.deleteSelectedItems()
                showDeleteDialog = false
            },
            onDismiss = { showDeleteDialog = false }
        )
    }

    if (showDialog) {
        CreateDirectoryDialog(
            onConfirm = { folderName ->
                viewModel.createDirAndReload(folderName)
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Filem - Inicio") },
                actions = {
                    // Boton para crear un nuevo directorio
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { showDialog = true },
                        contentAlignment = Alignment.CenterEnd,
                        content = {
                            Icon(
                                Icons.Default.AddCircle,
                                contentDescription = "Más opciones",
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    )
                }
            )
        },
        bottomBar = {
            BottomActionBar(
                visible = state.selectedItems.isNotEmpty(),
                onDeleteClick = { showDeleteDialog = true }
            )
        }
    ) { innerPadding ->
        // Contenido de la pantalla
        HubContent(
            state = state,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            onNavigateTo = onNavigateToFile,
            toggleSelection = { file -> viewModel.toggleSelection(file) }
            // Equivalente a "viewModel::toggleSelection", se puede cambiar
        )

    }

}
