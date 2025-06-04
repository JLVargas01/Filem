package com.spiralsoft.filem.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.io.File

@Composable
fun FileListContent(
    directories: List<File>,
    modifier: Modifier = Modifier,
    files: List<File>,
    selectedItems: Set<File>,
    onNavigateTo: (String) -> Unit,
    toggleSelection: (File) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(directories) { dir ->
            DirectoryItem(
                dir = dir,
                isSelected = selectedItems.contains(dir),
                onClick = {
                    if (selectedItems.isNotEmpty()) toggleSelection(dir)
                    else onNavigateTo(dir.absolutePath)
                },
                onLongClick = { toggleSelection(dir) }
            )
        }
        items(files) { file ->
            FileItem(
                file = file,
                isSelected = selectedItems.contains(file),
                onClick = {
                    if (selectedItems.isNotEmpty()) toggleSelection(file)
                    else onNavigateTo(file.absolutePath)
                },
                onLongClick = { toggleSelection(file) },
            )
        }
    }
}
