package com.spiralsoft.filem.navigation

import android.net.Uri
import android.os.Environment
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument

object AppNavigator {

    object Hub {
        const val ROUTE = "hub"
    }

    object Explorer {
        private const val BASE = "explorer"
        const val ROUTE = "$BASE/{path}"

        fun createRoute(path: String): String {
            return "$BASE/${Uri.encode(path)}"
        }

        fun navArguments(): List<NamedNavArgument> {
            return listOf(navArgument("path") { type = NavType.StringType })
        }

        fun extractPath(backStackEntry: NavBackStackEntry): String {
            return backStackEntry.arguments?.getString("path")?.let { Uri.decode(it) }
                ?: Environment.getExternalStorageDirectory().absolutePath
        }

    }
}
