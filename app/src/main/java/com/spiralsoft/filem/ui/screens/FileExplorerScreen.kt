package com.spiralsoft.filem.ui.screens

import android.os.Environment
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.spiralsoft.filem.viewmodel.FileExplorerViewModel
import com.spiralsoft.filem.viewmodel.SortMode
import java.io.File

@Composable
fun FileExplorerScreen(
    viewModel: FileExplorerViewModel = viewModel(),
    onNavigateTo: (String) -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val rootPath = Environment.getExternalStorageDirectory().absolutePath

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Explorador de archivos",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                viewModel.loadFiles(rootPath)
                onNavigateTo(rootPath)
            }) {
                Text("🏠 Inicio")
            }

            if (File(state.currentPath).parent != null) {
                Button(onClick = {
                    viewModel.goBack()
                    File(state.currentPath).parent?.let(onNavigateTo)
                }) {
                    Text("⬅️ Volver")
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "📂 ${state.currentPath}",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        SortOptions(currentSort = state.sortMode, onSortSelected = viewModel::changeSortMode)

        Spacer(modifier = Modifier.height(8.dp))

        if (state.files.isEmpty()) {
            Text(
                text = "No hay archivos en esta carpeta.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 20.dp)
            )
        } else {
            LazyColumn {
                items(state.files) { file ->
                    FileItem(file = file, onClick = {
                        if (file.isDirectory) {
                            viewModel.loadFiles(file.absolutePath)
                            onNavigateTo(file.absolutePath)
                        }
                    })
                }
            }
        }
    }
}

@Composable
fun FileItem(file: File, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 6.dp)
    ) {
        Text(
            text = if (file.isDirectory) "📁 ${file.name}" else "📄 ${file.name}",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun SortOptions(currentSort: SortMode, onSortSelected: (SortMode) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        DropdownMenuSortOption("Nombre", SortMode.BY_NAME, currentSort, onSortSelected)
        DropdownMenuSortOption("Fecha", SortMode.BY_DATE, currentSort, onSortSelected)
        DropdownMenuSortOption("Tamaño", SortMode.BY_SIZE, currentSort, onSortSelected)
    }
}

@Composable
fun DropdownMenuSortOption(label: String, mode: SortMode, current: SortMode, onSelect: (SortMode) -> Unit) {
    Button(
        onClick = { onSelect(mode) },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (mode == current) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
        )
    ) {
        Text(label)
    }
}
