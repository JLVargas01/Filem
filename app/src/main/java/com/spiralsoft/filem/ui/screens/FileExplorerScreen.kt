package com.spiralsoft.filem.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.spiralsoft.filem.viewmodel.FileExplorerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileExplorerScreen(
    directoryPath: String,
    viewModel: FileExplorerViewModel = viewModel(),
    onNavigateToFile: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(directoryPath) {
        viewModel.loadDirectory(directoryPath)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Filem") })
        }
    ) { innerPadding ->
        NavContent(
            state = state,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            onNavigateTo = onNavigateToFile
        )

    }
}
