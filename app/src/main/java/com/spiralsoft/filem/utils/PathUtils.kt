package com.spiralsoft.filem.utils

import android.os.Environment

fun String.cleanPath(): String {
    val rootPath = Environment.getExternalStorageDirectory().absolutePath
    return this.removePrefix(rootPath).let {
        it.ifEmpty { "/" }
    }
}
