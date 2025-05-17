package com.spiralsoft.filem

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spiralsoft.filem.ui.screens.FileExplorerScreen
import com.spiralsoft.filem.ui.screens.HubFileExplorerScreen
import android.net.Uri

object Routes {
    const val HUBEXPLORER = "hubexplorer"
    const val EXPLORER = "explorer"
}

@Composable
fun AppNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HUBEXPLORER
    ) {
        composable(Routes.HUBEXPLORER) {
            HubFileExplorerScreen(
                onNavigateTo = { path ->
                    navController.navigate("${Routes.EXPLORER}/${Uri.encode(path)}")
                }
            )
        }
        composable("${Routes.EXPLORER}/{path}") { backStackEntry ->
            val path = backStackEntry.arguments?.getString("path")?.let { Uri.decode(it) }
                ?: return@composable

            FileExplorerScreen(
                initialPath = path,
                onNavigateTo = { newPath ->
                    navController.navigate("${Routes.EXPLORER}/${Uri.encode(newPath)}")
                }
            )
        }
    }
}
