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
                "jpg", "jpeg", "png", "gif", "bmp" -> IMAGE
                "mp3", "wav", "ogg" -> AUDIO
                "mp4", "avi", "mkv" -> VIDEO
                "doc", "docx", "txt" -> DOCUMENT
                "pdf" -> PDF
                "zip" -> ARCHIVE
                "DATAFILE" -> DATAFILE
                else -> UNKNOWN
            }
        }
    }

}