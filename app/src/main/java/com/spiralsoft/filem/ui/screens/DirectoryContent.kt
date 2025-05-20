package com.spiralsoft.filem.ui.screens

import android.provider.ContactsContract.Directory
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import com.spiralsoft.filem.ui.components.DirectoryItem
import com.spiralsoft.filem.ui.components.FileItem
import com.spiralsoft.filem.viewmodel.DirectoryExplorerViewState

@Composable
fun DirectoryContent(
    state: DirectoryExplorerViewState, // Estado de la pantalla
    modifier: Modifier = Modifier, // Modificador para personalizar la apariencia
    onNavigateTo: (String) -> Unit // Función para navegar a una ruta
) {
    when {

        // Mostrar el indicador de carga
        state.isLoading -> {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        // Mostrar el contenido de la lista de directorios
        state.directories.isEmpty() && state.files.isEmpty() -> {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                Text("No se encontraron directorios disponibles.")
            }
        }

        // Mostrar el contenido de la lista de directorios
        else -> {
            LazyColumn(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Item para directorios
                items(state.directories) { dir ->
                    DirectoryItem(
                        dir = dir,
                        onClick = { onNavigateTo(dir.absolutePath) }
                    )
                }
                // Item de archivo
                items(state.files) { file ->
                    FileItem(
                        dir = file,
                        onClick = { onNavigateTo(file.absolutePath) }
                    )
                }
            }
        }

    }
}
