/*
* Esta es la pantalla al explorar archivos
* Se muestra cuando no se esta el directorio rootPath  "/DCIM", "/Pictures", etc
 */

package com.spiralsoft.filem.ui.screens

import com.spiralsoft.filem.viewmodel.FileExplorerViewModel
import android.os.Environment
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.Alignment
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileExplorerScreen(
    initialPath: String,
    viewModel: FileExplorerViewModel = viewModel(),
    onNavigateTo: (String) -> Unit
) {

    val state by viewModel.state.collectAsState()
    val rootPath = remember { Environment.getExternalStorageDirectory().absolutePath }
    val current = File(state.currentPath).canonicalPath
    val root = File(rootPath).canonicalPath
    val relativePath = current.removePrefix("$root/")
    val segments = if (current != root && current.startsWith(root)) relativePath.split("/") else emptyList()

    LaunchedEffect(initialPath) {
        viewModel.loadFiles(initialPath)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Filem") },
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
                if (segments.isNotEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        AssistChip(
                            onClick = {
                                onNavigateTo(root)
                            },
                            label = { Text("Inicio") },
                            colors = AssistChipDefaults.assistChipColors()
                        )
                        Row(
                            modifier = Modifier
                                .horizontalScroll(rememberScrollState())
                                .weight(1f),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            var cumulativePath = root
                            segments.forEachIndexed { index, segment ->
                                val isLast = index == segments.lastIndex
                                cumulativePath = "$cumulativePath/$segment"

                                AssistChip(
                                    onClick = {
                                        onNavigateTo(cumulativePath)
                                    },
                                    label = {
                                        Text(
                                            text = segment,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    },
                                    colors = if (isLast)
                                        AssistChipDefaults.assistChipColors(
                                            containerColor = MaterialTheme.colorScheme.primaryContainer
                                        )
                                    else
                                        AssistChipDefaults.assistChipColors()
                                )
                            }
                        }
                    }
                }
                val parent = File(state.currentPath).parent
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
                        onNavigateTo(root)
                    }) {
                        Icon(Icons.Default.Home, contentDescription = "Inicio")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Inicio")
                    }
                }
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
