package com.spiralsoft.filem.constants

import android.content.Context
import android.widget.Toast
import java.io.File
import com.spiralsoft.filem.R
import com.spiralsoft.filem.utils.FileOpener

enum class FileType(
    val iconRes: Int,
    val errorIconRes: Int = R.drawable.icon_error,
    val mimeType: String,
    val onClickAction: ((Context, File) -> Unit)
) {
    IMAGE(
        iconRes = R.drawable.icon_img0,
        mimeType = "image/*",
        onClickAction = { context, file ->
            if (FileOpener.canOpenFile(context, file, "video/*")) {
                FileOpener.openVideo(context, file)
            } else {
                Toast.makeText(context, "No hay aplicaciones disponibles para abrir este archivo", Toast.LENGTH_SHORT).show()
            }
        }
    ),
    AUDIO(
        iconRes = R.drawable.icon_audio0,
        mimeType = "audio/*",
        onClickAction = { context, file ->
            if (FileOpener.canOpenFile(context, file, "video/*")) {
                FileOpener.openAudio(context, file)
            } else {
                Toast.makeText(context, "No hay aplicaciones disponibles para abrir este archivo", Toast.LENGTH_SHORT).show()
            }
        }
    ),
    VIDEO(
        iconRes = R.drawable.icon_video0,
        mimeType = "video/*",
        onClickAction = { context, file ->
            if (FileOpener.canOpenFile(context, file, "video/*")) {
                FileOpener.openVideo(context, file)
            } else {
                Toast.makeText(context, "No hay aplicaciones disponibles para abrir este archivo", Toast.LENGTH_SHORT).show()
            }
        }
    ),
    DOCUMENT(
        iconRes = R.drawable.icon_document0,
        mimeType = "text/*",
        onClickAction = { context, file ->
            if (FileOpener.canOpenFile(context, file, "video/*")) {
                FileOpener.openText(context, file)
            } else {
                Toast.makeText(context, "No hay aplicaciones disponibles para abrir este archivo", Toast.LENGTH_SHORT).show()
            }
        }
    ),
    PDF(
        iconRes = R.drawable.icon_pdf0,
        mimeType = "application/pdf",
        onClickAction = { context, file ->
            if (FileOpener.canOpenFile(context, file, "video/*")) {
                FileOpener.openPdf(context, file)
            } else {
                Toast.makeText(context, "No hay aplicaciones disponibles para abrir este archivo", Toast.LENGTH_SHORT).show()
            }
        }
    ),
    ARCHIVE(
        iconRes = R.drawable.icon_archive0,
        mimeType = "application/zip",
        onClickAction = { context, file ->
            if (FileOpener.canOpenFile(context, file, "video/*")) {
                FileOpener.openCompressed(context, file)
            } else {
                Toast.makeText(context, "No hay aplicaciones disponibles para abrir este archivo", Toast.LENGTH_SHORT).show()
            }
        }
    ),
    DATAFILE(
        iconRes = R.drawable.icon_datafile0,
        mimeType = "application/octet-stream",
        onClickAction = { context, file ->
            if (FileOpener.canOpenFile(context, file, "video/*")) {
                FileOpener.openText(context, file)
            } else {
                Toast.makeText(context, "No hay aplicaciones disponibles para abrir este archivo", Toast.LENGTH_SHORT).show()
            }
        }
    ),
    UNKNOWN(
        iconRes = R.drawable.icon_unkdown,
        mimeType = "",
        onClickAction = { context, _ ->
            Toast.makeText(context, "No se puede abrir este tipo de archivo", Toast.LENGTH_SHORT).show()
        }
    );
    companion object {
        fun fromFile(file: File): FileType {
            val extension = file.extension.lowercase()
            return when (extension) {
                "jpg", "jpeg", "png", "gif", "bmp", "webp" -> IMAGE //Extenciones de imagenes
                "mp3", "wav", "ogg", "flac", "aac" -> AUDIO //Extenciones de audio
                "mp4", "avi", "mkv", "mov", "3gp", "webm" -> VIDEO // Extenciones de video
                "doc", "docx", "txt", "rtf", "odt" -> DOCUMENT // Extenciones de documentos
                "pdf" -> PDF // Extenciones de pdf
                "zip", "rar", "7z", "tar", "gz" -> ARCHIVE // Extenciones de archivos comprimidos
                "csv", "json", "xml", "bin", "dat" -> DATAFILE // Extenciones de archivos de datos
                else -> UNKNOWN // Desconicidos
            }
        }
    }
}

/* NOTA: Implementar un codigo como este en el futuro para evitar tanta repeticion
Esque me dio weba
fun doOpenFile(context: Context, file: File, type: String) {
    if (FileOpener.canOpenFile(context, file, type.mimeType)) {
        type.onClickAction(context, file)
    } else {
        Toast.makeText(context, "No hay apps para abrir este archivo", Toast.LENGTH_SHORT).show()
    }
}
*/