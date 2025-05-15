package com.spiralsoft.filem.ui

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
import com.spiralsoft.filem.ui.theme.ThemeViewModel

object Routes {
    const val Explorer = "explorer"
    const val Settings = "settings"
}

@Composable
fun AppNavHost(themeViewModel: ThemeViewModel) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "${Routes.Explorer}?path={path}"
    ) {

        composable(
            route = "${Routes.Explorer}?path={path}",
            arguments = listOf(
                navArgument("path") {
                    type = NavType.StringType
                    defaultValue = Environment.getExternalStorageDirectory().absolutePath
                }
            )
        ) { backStackEntry ->
            val path = backStackEntry.arguments?.getString("path") ?: ""
            FileExplorerScreen(
                path = path,
                onNavigateTo = { newPath ->
                    navController.navigate("${Routes.Explorer}?path=${Uri.encode(newPath)}")
                },
                onNavigateToSettings = {
                    navController.navigate(Routes.Settings)
                }
            )
        }

        composable(Routes.Settings) {
            SettingsScreen(navController, themeViewModel)
        }

    }
}
