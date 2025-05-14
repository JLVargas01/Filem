package com.spiralsoft.filem.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import android.os.Environment
import java.io.File

@Composable
fun FileExplorerScreen(path: String, onNavigateTo: (String) -> Unit) {

    val currentPath = remember(path) { path }
    val rootPath = Environment.getExternalStorageDirectory().absolutePath

    val files by remember(path) {
        mutableStateOf(getFiles(currentPath))
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = " Path: $currentPath",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Boton para volver al inicio
        Button(
            onClick = {
                onNavigateTo(rootPath)
            },
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text("Ir al Inicio")
        }

        // Botón para ir a ajustes
        Button(
            onClick = { onNavigateTo("settings") },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Ajustes")
        }

        // Botón para regresar a carpeta padre
        val parent = File(currentPath).parent
        if (parent != null) {
            Button(
                onClick = {
                    onNavigateTo(parent)
                },
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text("Volver")
            }
        }

        LazyColumn {
            items(files) { file ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (file.isDirectory) {
                                onNavigateTo(file.absolutePath)
                            }
                        }
                        .padding(vertical = 6.dp)
                ) {
                    Text(
                        text = if (file.isDirectory) "Dir ${file.name}" else "Fil ${file.name}",
                        style = MaterialTheme.typography.bodyLarge
                    )
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
