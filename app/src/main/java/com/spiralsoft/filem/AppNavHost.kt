package com.spiralsoft.filem

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spiralsoft.filem.ui.screens.HubFileExplorerScreen
import com.spiralsoft.filem.ui.screens.FileExplorerScreen

@Composable
fun AppNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppNavigator.Hub.ROUTE
    ) {

        // Pantalla principal del rootPath (Hub)
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
        composable(
            route = AppNavigator.Explorer.ROUTE,
            arguments = AppNavigator.Explorer.navArguments()
        ) { backStackEntry ->
            val path = AppNavigator.Explorer.extractPath(backStackEntry)
            FileExplorerScreen(
                directoryPath = path,
                onNavigateToFile = { nextPath ->
                    navController.navigate(AppNavigator.Explorer.createRoute(nextPath))
                }
            )
        }

    }
}
