package com.spiralsoft.filem

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spiralsoft.filem.ui.screens.HubFileExplorerScreen

@Composable
fun AppNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppNavigator.Hub.ROUTE
    ) {

        // Pantalla principal (Hub)
        composable(
            route = AppNavigator.Hub.ROUTE
        ) {
            HubFileExplorerScreen(
                onNavigateToFile = { path ->
                    navController.navigate(AppNavigator.Explorer.createRoute(path))
                }
            )
        }

        // Pantalla al navegar por directorios
        //TODO

    }
}
