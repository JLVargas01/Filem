package com.spiralsoft.filem

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.spiralsoft.filem.ui.AppNavHost
import com.spiralsoft.filem.ui.theme.FilemTheme
import com.spiralsoft.filem.ui.theme.ThemeViewModel
import com.spiralsoft.filem.ui.theme.ThemeViewModelFactory

class MainActivity : ComponentActivity() {

    // Lanzador moderno de permisos
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        if (!allGranted) {
            // Aquí puedes mostrar un mensaje o cerrar la app si lo deseas
        }
    }

    private val themeViewModel: ThemeViewModel by viewModels {
        ThemeViewModelFactory(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
            FilemTheme(useDarkTheme = isDarkTheme) {
                AppNavHost(themeViewModel)
            }
        }

        // Pedimos permisos si no están concedidos
        if (!PermissionUtils.hasPermissions(this)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                PermissionUtils.requestPermissions(this)
            } else {
                PermissionUtils.requestPermissionsPreR(permissionLauncher)
            }
        }

    }

}
