/*
** Define rutas, pantallas y controladores
*/

package com.spiralsoft.filem.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spiralsoft.filem.ui.screens.DetailScreen
import com.spiralsoft.filem.ui.screens.HomeScreen

@Composable
fun AppNavHost() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(onNavigateToDetails = {
                navController.navigate("details")
            })
        }
        composable("details") {
            DetailScreen()
        }
    }
}
