package com.spiralsoft.filem

import android.os.Environment
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.io.File

@Composable
fun FileExplorerScreen() {
    var currentPath by remember {
        mutableStateOf(Environment.getExternalStorageDirectory().absolutePath)
    }

    var files by remember(currentPath) {
        mutableStateOf(getFiles(currentPath))
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "📂 $currentPath",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Botón para regresar a carpeta padre
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
                                currentPath = file.absolutePath
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

fun getFiles(path: String): List<File> {
    val dir = File(path)
    return dir.listFiles()
        ?.filter { it.exists() && it.canRead() }
        ?.sortedWith(compareBy<File> { !it.isDirectory }.thenBy { it.name.lowercase() })
        ?: emptyList()
}
