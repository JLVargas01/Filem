package com.spiralsoft.filem.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.spiralsoft.filem.viewmodel.SortMode

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
