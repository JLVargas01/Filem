package com.spiralsoft.filem.domain.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File

object FileOpener {

    // Abrir imagenes en apps que lo soporten
    fun openImage(context: Context, file: File) {
        openFileWithMime(context, file, "image/*")
    }

    // Abrir archivos de audio en apps que lo soporten
    fun openAudio(context: Context, file: File) {
        openFileWithMime(context, file, "audio/*")
    }

    // Abrir archivos de video en apps que lo soporten
    fun openVideo(context: Context, file: File) {
        openFileWithMime(context, file, "video/*")
    }

    // Abrir archivos pdf en apps que lo soporten
    fun openPdf(context: Context, file: File) {
        openFileWithMime(context, file, "application/pdf")
    }

    // Abrir archivos de excel en apps que lo soporten
    fun openExcel(context: Context, file: File) {
        openFileWithMime(context, file, "application/vnd.ms-excel")
    }

    // Abrir archivos comprimidos
    fun openCompressed(context: Context, file: File) {
        openFileWithMime(context, file, "application/zip")
    }

    // Abrir archivos de texto en apps que lo soporten
    fun openText(context: Context, file: File) {
        openFileWithMime(context, file, "text/plain")
    }

    // Verificar si existe una app que pueda abrir el archivo
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
        val packageManager = context.packageManager
        val resolveInfoList = packageManager.queryIntentActivities(intent,
            PackageManager.MATCH_DEFAULT_ONLY
        )
        return resolveInfoList.isNotEmpty()
    }

    // Abrir archivos con un intent
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
            context.startActivity(Intent.createChooser(intent, "Abrir con:"))
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "No se pudo abrir el archivo", Toast.LENGTH_SHORT).show()
        }
    }

}