package com.spiralsoft.filem.constants

import com.spiralsoft.filem.R
import java.io.File

enum class FileType(val iconRes: Int) {

    IMAGE(R.drawable.icon_img0),
    AUDIO(R.drawable.icon_audio0),
    VIDEO(R.drawable.icon_video0),
    DOCUMENT(R.drawable.icon_document0),
    PDF(R.drawable.icon_pdf0),
    ARCHIVE(R.drawable.icon_archive0),
    DATAFILE(R.drawable.icon_datafile0),
    UNKNOWN(R.drawable.icon_unkdown);

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
