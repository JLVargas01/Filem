package com.spiralsoft.filem

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spiralsoft.filem.ui.screens.FileExplorerScreen

object Routes {
    const val EXPLORER = "explorer"
}

@Composable
fun AppNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.EXPLORER
    ) {
        composable(Routes.EXPLORER) {
            FileExplorerScreen(
                onNavigateTo = {}
            )
        }
    }

}
