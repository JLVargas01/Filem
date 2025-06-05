/**
 * Manejo de navegación de la aplicación
 */
package com.spiralsoft.filem.presentation.navigation

import com.spiralsoft.filem.presentation.navigation.AppNavigator.Explorer.createRoute
import com.spiralsoft.filem.presentation.screen.directory.DirectoryExplorerScreen
import com.spiralsoft.filem.presentation.screen.hub.HubFileExplorerScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import java.nio.file.Path

@Composable
fun AppNavHost() {

    val navController = rememberNavController() // Controlador de navegación

    NavHost(
        navController = navController,
        startDestination = AppNavigator.Hub.ROUTE
    ) {

        // Pantalla principal del rootPath (Hub)
        composable(
            route = AppNavigator.Hub.ROUTE
        ) {
            HubFileExplorerScreen(
                onNavigateToFile = {
                    path -> navController.navigate(createRoute(path))
                }
            )
        }

        // Pantalla de explorador de archivos
        composable(
            route = AppNavigator.Explorer.ROUTE,
            arguments = AppNavigator.Explorer.navArguments()
        ) { backStackEntry ->
            val path: Path = AppNavigator.Explorer.extractPath(backStackEntry)
            DirectoryExplorerScreen(
                initialPath = path,
                onNavigateBack = { navController.popBackStack() }
            )
        }

    }
}
