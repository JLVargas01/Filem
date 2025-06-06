package com.spiralsoft.filem.ui.screens

import com.spiralsoft.filem.viewmodel.FileExplorerViewModel
import android.os.Environment
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import java.io.File

@Composable
fun FileExplorerScreen(
    viewModel: FileExplorerViewModel = viewModel(),
    onNavigateTo: (String) -> Unit,
) {
    val currentPath by viewModel.currentPath.collectAsState()
    val files by viewModel.files.collectAsState()
    val rootPath = Environment.getExternalStorageDirectory().absolutePath

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(16.dp)
    ) {
        Text(
            text = "Explorador de archivos",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        Button(
            onClick = {
                viewModel.loadFiles(rootPath)
                onNavigateTo(rootPath) // solo si vas a otra pantalla
            },
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text("⚫ Ir al Inicio")
        }

        Text(
            text = "📂 $currentPath",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (File(currentPath).parent != null) {
            Button(
                onClick = {
                    viewModel.goBack()
                    File(currentPath).parent?.let { onNavigateTo(it) } // opcional
                },
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text("⬅️ Volver")
            }
        }

        LazyColumn {
            items(files) { file ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (file.isDirectory) {
                                viewModel.loadFiles(file.absolutePath)
                                onNavigateTo(file.absolutePath) // opcional
                            }
                        }
                        .padding(vertical = 6.dp)
                ) {
                    Text(
                        text = if (file.isDirectory) "📁 ${file.name}" else "📄 ${file.name}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
