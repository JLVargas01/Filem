/**
 * Contenido de la pantalla principal
 * solo lista y muestra el contenido de la carpeta raiz "/"
 */
package com.spiralsoft.filem.presentation.screen.hub

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import com.spiralsoft.filem.presentation.components.FileListContent
import java.io.File

@Composable
fun HubContent(
    state: HubDirectoryExplorerState, // Estado de la pantalla
    modifier: Modifier = Modifier, // Modificador para personalizar la apariencia
    onNavigateTo: (String) -> Unit, // Función para navegar a una ruta
    toggleSelection: (File) -> Unit // Función para seleccionar un elemento
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
                Text("Vacio")
            }
        }
        // Mostrar el contenido de la lista de directorios
        else -> {
            FileListContent(
                modifier = modifier,
                directories = state.directories,
                files = state.files,
                selectedItems = state.selectedItems,
                onNavigateTo = onNavigateTo,
                toggleSelection = toggleSelection
            )
        }
    }

}
