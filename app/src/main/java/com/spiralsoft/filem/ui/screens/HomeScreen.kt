package com.spiralsoft.filem.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Column


@Composable
fun HomeScreen(onNavigateToDetails: () -> Unit) {
    Column {
        Text("Pantalla Principal")
        Button(onClick = onNavigateToDetails) {
            Text("Ir a Detalles")
        }
    }
}

@Composable
fun DetailScreen() {
    Text("Pantalla de Detalles")
}
