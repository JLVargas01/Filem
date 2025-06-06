/**
 * Representa una lista de archivos(FileListContent),
 * con sus caracteristicas y las acciones que puede realizar
 */
package com.spiralsoft.filem.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.nio.file.Path

@Composable
fun FileListContent(
    directories: List<Path>,
    modifier: Modifier = Modifier,
    files: List<Path>,
    selectedItems: Set<Path>,
    onNavigateTo: (Path) -> Unit,
    toggleSelection: (Path) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(directories) { dir ->
            DirectoryItem(
                pathDir = dir,
                isSelected = selectedItems.contains(dir),
                onClick = {
                    if (selectedItems.isNotEmpty()) toggleSelection(dir)
                    else onNavigateTo(dir)
                },
                onLongClick = { toggleSelection(dir) }
            )
        }
        items(files) { file ->
            FileItem(
                pathFile = file,
                isSelected = selectedItems.contains(file),
                onClick = {
                    if (selectedItems.isNotEmpty()) toggleSelection(file)
                    else onNavigateTo(file)
                },
                onLongClick = { toggleSelection(file) },
            )
        }
    }
}
