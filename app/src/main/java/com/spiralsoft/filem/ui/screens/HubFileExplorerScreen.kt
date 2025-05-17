/*
* Esta es la pantalla hub del explorador de archivos
* Se muestra cuand se esta en la carpeta raiz  "/"
 */

package com.spiralsoft.filem.ui.screens

import com.spiralsoft.filem.viewmodel.FileExplorerViewModel
import android.os.Environment
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HubFileExplorerScreen(
    viewModel: FileExplorerViewModel = viewModel(),
    onNavigateTo: (String) -> Unit,
) {
    val state by viewModel.state.collectAsState()

    val rootPath = remember { Environment.getExternalStorageDirectory().absolutePath }
    val current = File(state.currentPath).canonicalPath

    LaunchedEffect(Unit) {
        viewModel.loadFiles(rootPath)
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
                if (state.files.isEmpty()) {
                    Text(
                        text = "No hay archivos en el almacenamiento.",
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
