package com.spiralsoft.filem

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spiralsoft.filem.ui.screens.DirectoryExplorerScreen
import com.spiralsoft.filem.ui.screens.HubFileExplorerScreen

@Composable
fun AppNavHost() {

    val navController = rememberNavController() // Controlador de navegación

    NavHost(
        navController = navController,
        startDestination = AppNavigator.Hub.ROUTE
    ) {

        /**
        * Pantalla principal del rootPath (Hub)
        */
        composable(
            route = AppNavigator.Hub.ROUTE
        ) {
            HubFileExplorerScreen(
                onNavigateToFile = { path ->
                    navController.navigate(AppNavigator.Explorer.createRoute(path))
                }
            )
        }

        /**
         * Pantalla de explorador de archivos
        */
        composable(
            route = AppNavigator.Explorer.ROUTE,
            arguments = AppNavigator.Explorer.navArguments()
        ) { backStackEntry ->
            val path = AppNavigator.Explorer.extractPath(backStackEntry)
            DirectoryExplorerScreen(
                initialPath = path,
                onNavigateBack = { navController.popBackStack() }
            )
        }

    }
}
