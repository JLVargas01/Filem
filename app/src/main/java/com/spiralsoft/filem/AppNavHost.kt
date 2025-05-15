package com.spiralsoft.filem

import android.net.Uri
import android.os.Environment
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.spiralsoft.filem.ui.screens.FileExplorerScreen
import com.spiralsoft.filem.ui.screens.SettingsScreen
import com.spiralsoft.filem.viewmodel.ThemeViewModel

object Routes {
    const val EXPLORER = "explorer"
    const val SETTINGS = "settings"
}

@Composable
fun AppNavHost(themeViewModel: ThemeViewModel) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.EXPLORER
    ) {
        composable(Routes.EXPLORER) {
            FileExplorerScreen(
                onNavigateTo = {},
                onNavigateToSettings = {
                    navController.navigate(Routes.SETTINGS)
                }
            )
        }

        composable(Routes.SETTINGS) {
            SettingsScreen(navController, themeViewModel)
        }

    }
}
