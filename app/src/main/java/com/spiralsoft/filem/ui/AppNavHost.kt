/*
** Define rutas, pantallas y controladores
*/

package com.spiralsoft.filem.ui

import com.spiralsoft.filem.ui.screens.FileExplorerScreen
import android.net.Uri
import androidx.navigation.NavType
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import android.os.Environment
import com.spiralsoft.filem.ui.screens.SettingsScreen

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "explorer") {

        // Ruta inicial (sin path), usamos el path por defecto
        composable("explorer") {
            val defaultPath = Environment.getExternalStorageDirectory().absolutePath
            FileExplorerScreen(
                path = defaultPath,
                onNavigateTo = { newPath -> navController.navigate("explorer?path=${Uri.encode(newPath)}") }
            )
        }

        // Ruta con path personalizado
        composable(
            "explorer?path={path}",
            arguments = listOf(
                navArgument("path") {
                    type = NavType.StringType
                    nullable = false
                    defaultValue = Environment.getExternalStorageDirectory().absolutePath
                }
            )
        ) { backStackEntry ->
            val path = backStackEntry.arguments?.getString("path")!!
            FileExplorerScreen(
                path = path,
                onNavigateTo = { newPath -> navController.navigate("explorer?path=${Uri.encode(newPath)}") }
            )
        }

        composable("Settings"){
            SettingsScreen(onBack = {
                navController.popBackStack()
            })
        }

    }
}
