package com.spiralsoft.filem.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.content.FileProvider
import java.util.Locale
import java.io.File

object FileOpener {

    fun openFile(context: Context, file: File) {
        val uri = try {
            FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                file
            )
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            Toast.makeText(context, "Archivo no autorizado para compartir", Toast.LENGTH_SHORT).show()
            return
        }

        val mime = getMimeType(file) ?: "*/*"

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, mime)
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK
        }

        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "No hay ninguna app que pueda abrir este archivo", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Error al abrir el archivo", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getMimeType(file: File): String? {
        val extension = file.extension.lowercase(Locale.ROOT)
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
    }
}
