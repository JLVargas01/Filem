package com.spiralsoft.filem.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import android.content.pm.PackageManager
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.net.toUri

object PermissionUtils {

    /**
     * Verifica si la app tiene permisos para acceder al almacenamiento externo
     */
    fun hasPermissions(context: Context): Boolean {
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                // Android 11+ requiere permiso especial
                Environment.isExternalStorageManager()
            }
            else -> {
                // Android 8–10: permisos clásicos
                val read = ContextCompat.checkSelfPermission(
                    context, Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED

                val write = ContextCompat.checkSelfPermission(
                    context, Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED

                read && write
            }
        }
    }

    /**
     * Solicita los permisos necesarios dependiendo de la versión de Android
     */
    fun requestAppropriatePermissions(
        context: Context,
        legacyLauncher: ActivityResultLauncher<Array<String>>,
        activity: Activity
    ) {
        if (!hasPermissions(context)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                requestAllFilesAccessPermission(activity)
            } else {
                requestLegacyStoragePermissions(legacyLauncher)
            }
        }
    }

    /**
     * Solicita permisos clásicos en Android 8–10
     */
    fun requestLegacyStoragePermissions(launcher: ActivityResultLauncher<Array<String>>) {
        launcher.launch(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        )
    }

    /**
     * Solicita el permiso especial en Android 11+ para acceso total a archivos
     */
    @RequiresApi(Build.VERSION_CODES.R)
    fun requestAllFilesAccessPermission(activity: Activity) {
        val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
            data = ("package:${activity.packageName}").toUri()
        }
        activity.startActivity(intent)
    }

    /**
     * Llamar desde onResume() para confirmar si se concedió el permiso después de ir a ajustes
     */
    fun isPermissionGrantedAfterSettings(context: Context): Boolean {
        return hasPermissions(context)
    }

}
