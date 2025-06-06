package com.spiralsoft.filem.domain.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File

object FileOpener {

    // Metodo genérico para abrir cualquier archivo
    fun openFile(context: Context, file: File, mimeType: String) {
        val uri = getUriForFile(context, file)
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, mimeType)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "No se encontró una app para abrir este archivo", Toast.LENGTH_SHORT).show()
        }
    }

    // Verifica si hay una app que pueda abrir el archivo
    fun canOpenFile(context: Context, file: File, mimeType: String): Boolean {
        val uri = getUriForFile(context, file)
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, mimeType)
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }

        val packageManager = context.packageManager
        return intent.resolveActivity(packageManager) != null
    }

    // Métodos específicos (pueden usar el genérico)
    fun openImage(context: Context, file: File) = openFile(context, file, "image/*")
    fun openAudio(context: Context, file: File) = openFile(context, file, "audio/*")
    fun openVideo(context: Context, file: File) = openFile(context, file, "video/*")
    fun openText(context: Context, file: File) = openFile(context, file, "text/*")
    fun openPdf(context: Context, file: File) = openFile(context, file, "application/pdf")
    fun openCompressed(context: Context, file: File) = openFile(context, file, "application/zip")

    // Generador seguro de URI usando FileProvider
    private fun getUriForFile(context: Context, file: File): Uri {
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider", // Debe coincidir con tu AndroidManifest
            file
        )
    }

}
