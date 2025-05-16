package com.spiralsoft.filem.ui.screens

import com.spiralsoft.filem.viewmodel.FileExplorerViewModel
import com.spiralsoft.filem.viewmodel.SortMode
import android.os.Environment
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        HorizontalDivider(modifier = Modifier.padding(bottom = 12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = {
                viewModel.loadFiles(rootPath)
                onNavigateTo(rootPath)
            }) {
                Icon(Icons.Default.Home, contentDescription = "Inicio")
                Spacer(modifier = Modifier.width(4.dp))
                Text("Inicio")
            }

            File(state.currentPath).parent?.let {
                Button(onClick = {
                    viewModel.goBack()
                    onNavigateTo(it)
                }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Volver")
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
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        tonalElevation = 2.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            Text(
                text = if (file.isDirectory) "📁 ${file.name}" else "📄 ${file.name}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun SortOptions(currentSort: SortMode, onSortSelected: (SortMode) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        SortChip("Nombre", SortMode.BY_NAME, currentSort, onSortSelected)
        SortChip("Fecha", SortMode.BY_DATE, currentSort, onSortSelected)
        SortChip("Tamaño", SortMode.BY_SIZE, currentSort, onSortSelected)
    }
}

@Composable
fun SortChip(label: String, mode: SortMode, current: SortMode, onSelect: (SortMode) -> Unit) {
    FilterChip(
        selected = mode == current,
        onClick = { onSelect(mode) },
        label = { Text(label) }
    )
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
