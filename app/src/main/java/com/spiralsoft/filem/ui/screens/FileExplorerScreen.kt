package com.spiralsoft.filem.ui.screens

import com.spiralsoft.filem.R
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileExplorerScreen(
    viewModel: FileExplorerViewModel = viewModel(),
    onNavigateTo: (String) -> Unit,
) {

    val state by viewModel.state.collectAsState()
    val rootPath = Environment.getExternalStorageDirectory().absolutePath
    val parent = File(state.currentPath).parent
    val isRoot = rootPath == state.currentPath

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Explorador de archivos") },
                actions = {
                    SortMenu(
                        currentSort = state.sortMode,
                        onSortSelected = viewModel::changeSortMode
                    )
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                if (!isRoot) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(onClick = {
                            viewModel.goBack()
                            parent?.let(onNavigateTo)
                        }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Volver")
                        }

                        Button(onClick = {
                            viewModel.loadFiles(rootPath)
                            onNavigateTo(rootPath)
                        }) {
                            Icon(Icons.Default.Home, contentDescription = "Inicio")
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Inicio")
                        }
                    }
                }

                Text(
                    text = "📂 ${state.currentPath}",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                if (state.files.isEmpty()) {
                    Text(
                        text = "No hay archivos en esta carpeta.",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 24.dp)
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
    )
}

@Composable
fun FileItem(
    file: File,
    onClick: () -> Unit
) {

    val iconRes = if (file.isDirectory)
        R.drawable.icon_folder0
        else R.drawable.icon_file0

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        tonalElevation = 2.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = "Carpeta",
                modifier = Modifier
                    .size(32.dp)
                    .padding(end = 12.dp),
                tint = Color.Unspecified
            )
            Text(
                text = file.name,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun SortMenu(
    currentSort: SortMode,
    onSortSelected: (SortMode) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(Icons.Default.Settings, contentDescription = "Ordenar")
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        DropdownMenuItem(
            text = { Text("Ordenar por nombre") },
            onClick = {
                onSortSelected(SortMode.BY_NAME)
                expanded = false
            }
        )
        DropdownMenuItem(
            text = { Text("Ordenar por fecha") },
            onClick = {
                onSortSelected(SortMode.BY_DATE)
                expanded = false
            }
        )
        DropdownMenuItem(
            text = { Text("Ordenar por tamaño") },
            onClick = {
                onSortSelected(SortMode.BY_SIZE)
                expanded = false
            }
        )
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
