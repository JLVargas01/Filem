package com.spiralsoft.filem.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.material3.Button

@Composable
fun HomeScreen(onNavigateToDetails: () -> Unit) {
        Text("Pantalla Principal")
        Button(onClick = onNavigateToDetails) {
            Text("Ir a Detalles")
        }
}

@Composable
fun DetailScreen() {
    Text("Pantalla de Detalles")
}
