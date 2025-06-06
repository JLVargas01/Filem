/**
 * Ayuda para controlar y formar rutas de navegación
 */
package com.spiralsoft.filem.presentation.navigation

import android.net.Uri
import android.os.Environment
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument

object AppNavigator {

    object Hub {
        const val ROUTE: String = "hub"
    }

    // Objeto para controlar la navegación del explorador de archivos
    object Explorer {

        private const val BASE: String = "explorer"
        const val ROUTE: String = "$BASE/{path}"

        // Construye una ruta de navegación para el explorador de archivos
        fun createRoute(path: String): String {
            return "$BASE/${Uri.encode(path)}"
        }

        // Obtiene los argumentos de navegación para el explorador de archivos
        fun navArguments(): List<NamedNavArgument> {
            return listOf(navArgument("path") { type = NavType.StringType })
        }

        // Extrae la ruta de navegación del explorador de archivos
        fun extractPath(backStackEntry: NavBackStackEntry): String {
            return backStackEntry.arguments?.getString("path")?.let { Uri.decode(it) }
                ?: Environment.getExternalStorageDirectory().absolutePath
        }

    }
}
