package com.spiralsoft.filem.ui.screens

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileExplorerScreen(
    path: String,
    onNavigateTo: (String) -> Unit,
    onNavigateToSettings: () -> Unit
) {
    var currentPath by remember { mutableStateOf(path) }
    var files by remember(currentPath) { mutableStateOf(getFiles(currentPath)) }
    val rootPath = Environment.getExternalStorageDirectory().absolutePath

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Explorador de archivos") },
                actions = {
                    IconButton(onClick = { onNavigateToSettings() }) {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = "Ajustes")
                    }
                }
            )
        }
    ) { innerPadding ->

        Button(
            onClick = {
                onNavigateTo(rootPath)
            },
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text("Ir al Inicio")
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = "📂 $currentPath",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            if (File(currentPath).parent != null) {
                Button(
                    onClick = {
                        currentPath = File(currentPath).parent!!
                        files = getFiles(currentPath)
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
                                    val newPath = file.absolutePath
                                    currentPath = newPath
                                    files = getFiles(newPath)
                                    onNavigateTo(newPath)
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
}

fun getFiles(path: String): List<File> {
    val dir = File(path)
    return dir.listFiles()
        ?.filter { it.exists() && it.canRead() }
        ?.sortedWith(compareBy<File> { !it.isDirectory }.thenBy { it.name.lowercase() })
        ?: emptyList()
}
