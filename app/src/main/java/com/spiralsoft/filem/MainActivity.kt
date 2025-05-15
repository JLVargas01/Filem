package com.spiralsoft.filem

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import com.spiralsoft.filem.utils.PermissionUtils
import android.widget.Toast

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestStoragePermissions()
        setContent {
            AppNavHost()
        }
    }

    private val storagePermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        if (!allGranted) {
            Toast.makeText(this, "Se requieren permisos para usar la app", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun requestStoragePermissions() {
        if (!PermissionUtils.hasPermissions(this)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                PermissionUtils.requestAllFilesAccessPermission(this)
            } else {
                PermissionUtils.requestLegacyStoragePermissions(storagePermissionLauncher)
            }
        }
    }

}
