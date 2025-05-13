package com.spiralsoft.filem

import android.os.Bundle
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spiralsoft.filem.ui.theme.FilemTheme
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FilemTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    FileExplorerScreen()
                }
            }
        }
    }

}

@Composable
fun FileExplorerScreen() {
    var currentPath by remember { mutableStateOf(Environment.getExternalStorageDirectory().absolutePath) }
    var files by remember { mutableStateOf(listFiles(currentPath)) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Ruta actual: $currentPath", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(files) { file ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (file.isDirectory) {
                                currentPath = file.absolutePath
                                files = listFiles(currentPath)
                            }
                        }
                        .padding(8.dp)
                ) {
                    Text(text = if (file.isDirectory) "📁 ${file.name}" else "📄 ${file.name}")
                }
            }
        }
    }
}

fun listFiles(path: String): List<File> {
    val file = File(path)
    return file.listFiles()?.filter { it.canRead() }?.toList() ?: emptyList()
}
