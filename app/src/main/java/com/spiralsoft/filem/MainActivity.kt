/*
 * Copyright (c) 2025 Jose Luis Vargas Ibarra
 *
 * This file is part of Filem.
 *
 * Filem is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Filem is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.spiralsoft.filem

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import com.spiralsoft.filem.utils.PermissionUtils
import android.widget.Toast
import com.spiralsoft.filem.ui.theme.FilemTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestStoragePermissions()
        setContent {
            FilemTheme {
                AppNavHost()
            }
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
