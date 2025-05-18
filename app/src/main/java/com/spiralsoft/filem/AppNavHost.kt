package com.spiralsoft.filem

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spiralsoft.filem.ui.screens.FileExplorerScreen
import com.spiralsoft.filem.ui.screens.HubFileExplorerScreen
import android.net.Uri

sealed class Screen(val route: String) {
    object Hub : Screen("hub")
    object Explorer : Screen("explorer/{path}") {
        fun createRoute(path: String) = "explorer/${Uri.encode(path)}"
    }
}

@Composable
fun AppNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppNavigator.Hub.ROUTE
    ) {
        composable(AppNavigator.Hub.ROUTE) {
            HubFileExplorerScreen(
                onNavigateTo = { path ->
                    navController.navigate(AppNavigator.Explorer.createRoute(path))
                }
            )
        }

        composable(
            route = AppNavigator.Explorer.ROUTE,
            arguments = listOf(AppNavigator.Explorer.navArgument())
        ) { backStackEntry ->
            val path = AppNavigator.Explorer.extractPath(backStackEntry)

            FileExplorerScreen(
                initialPath = path,
                onNavigateTo = { newPath ->
                    navController.navigate(AppNavigator.Explorer.createRoute(newPath))
                }
            )
        }
    }
}
