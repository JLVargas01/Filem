package com.spiralsoft.filem.ui.screens

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
import com.spiralsoft.filem.viewmodel.HubFileExplorerState

@Composable
fun HubContent(
    state: HubFileExplorerState,
    modifier: Modifier = Modifier,
    onNavigateTo: (String) -> Unit
) {
    when {
        state.isLoading -> {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        state.directories.isEmpty() -> {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                Text("No se encontraron directorios disponibles.")
            }
        }

        else -> {
            LazyColumn(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.directories) { dir ->
                    DirectoryItem(
                        dir = dir,
                        onClick = { onNavigateTo(dir.absolutePath) }
                    )
                }
            }
        }
    }
}
