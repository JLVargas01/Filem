package com.spiralsoft.filem.constants

import android.content.Context
import com.spiralsoft.filem.R
import java.io.File
import com.spiralsoft.filem.utils.FileOpener

enum class FileType(
    val iconRes: Int,
    val errorIconRes: Int = R.drawable.icon_error,
    val description: String,
    val mimeType: String,
    val onClickAction: ((Context, File) -> Unit)
) {


    IMAGE(
        iconRes = R.drawable.icon_img0,
        description = "Imagenes",
        mimeType = "image/*",
        onClickAction = { context, file ->
            FileOpener.openImage(context, file)
        }
    ),

    AUDIO(
        iconRes = R.drawable.icon_audio0,
        description = "Audios",
        mimeType = "audio/*",
        onClickAction = { context, file ->
            FileOpener.openAudio(context, file)
        }
    ),

    VIDEO(
        iconRes = R.drawable.icon_video0,
        description = "Videos",
        mimeType = "video/*",
        onClickAction = { context, file ->
            FileOpener.openVideo(context, file)
        }
    ),

    DOCUMENT(
        iconRes = R.drawable.icon_document0,
        description = "Documentos",
        mimeType = "text/*",
        onClickAction = { context, file ->
            FileOpener.openText(context, file)
        }
    ),

    PDF(
        iconRes = R.drawable.icon_pdf0,
        description = "PDF",
        mimeType = "application/pdf",
        onClickAction = { context, file ->
            FileOpener.openPdf(context, file)
        }
    ),

    ARCHIVE(
        iconRes = R.drawable.icon_archive0,
        description = "Archivos comprimidos",
        mimeType = "application/zip",
        onClickAction = { context, file ->
            FileOpener.openGeneric(context, file)
        }
    ),

    DATAFILE(
        iconRes = R.drawable.icon_datafile0,
        description = "Archivos de datoss",
        mimeType = "application/octet-stream",
        onClickAction = { context, file ->
            FileOpener.openGeneric(context, file)
        }
    ),

    UNKNOWN(
        iconRes = R.drawable.icon_unkdown,
        description = "Desconocido",
        mimeType = "*/*",
        onClickAction = { context, file ->
            FileOpener.openGeneric(context, file)
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
