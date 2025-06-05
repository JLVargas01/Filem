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
import java.nio.file.Path
import java.nio.file.Paths

object AppNavigator {

    object Hub {
        const val ROUTE: String = "hub"
    }

    // Objeto para controlar la navegación del explorador de archivos
    object Explorer {

        private const val BASE: String = "explorer"
        const val ROUTE: String = "$BASE/{path}"

        // Construye una ruta de navegación para el explorador de archivos
        fun createRoute(path: Path): Path {
            val route = "$BASE/${Uri.encode(path.toString())}"
            return Paths.get(route)
        }

        // Obtiene los argumentos de navegación para el explorador de archivos
        fun navArguments(): List<NamedNavArgument> {
            return listOf(navArgument("path") { type = NavType.StringType })
        }

        // Extrae la ruta de navegación del explorador de archivos
        fun extractPath(backStackEntry: NavBackStackEntry): Path {
            val pathString = backStackEntry.arguments?.getString("path")
                ?.let { Uri.decode(it) }
                ?: Environment.getExternalStorageDirectory().absolutePath
            return Paths.get(pathString)
        }

    }
}
