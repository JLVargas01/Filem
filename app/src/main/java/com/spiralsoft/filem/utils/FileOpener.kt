package com.spiralsoft.filem.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File

object FileOpener {

    fun openImage(context: Context, file: File) {
        openFileWithMime(context, file, "image/*")
    }

    fun openAudio(context: Context, file: File) {
        openFileWithMime(context, file, "audio/*")
    }

    fun openVideo(context: Context, file: File) {
        openFileWithMime(context, file, "video/*")
    }

    fun openPdf(context: Context, file: File) {
        openFileWithMime(context, file, "application/pdf")
    }

    fun openText(context: Context, file: File) {
        openFileWithMime(context, file, "text/plain")
    }

    fun openGeneric(context: Context, file: File) {
        openFileWithMime(context, file, "*/*")
    }

    fun canOpenFile(context: Context, file: File, mimeType: String): Boolean {
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, mimeType)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        // Busca actividades que puedan manejar el intent
        val packageManager = context.packageManager
        val resolveInfoList = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)

        return resolveInfoList.isNotEmpty()
    }

    private fun openFileWithMime(context: Context, file: File, mimeType: String) {
        try {
            val uri: Uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                file
            )

            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, mimeType)
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK
            }

            context.startActivity(intent)

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "No se pudo abrir el archivo", Toast.LENGTH_SHORT).show()
        }
    }
}

