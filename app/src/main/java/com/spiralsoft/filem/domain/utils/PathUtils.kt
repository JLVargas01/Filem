/**
 * Utils para limpiar la ruta
 * "/storage/emulated/0/" -> "/"
 */
package com.spiralsoft.filem.domain.utils

import android.os.Environment

// Limpia la ruta para que solo muestre el nombre del directorio
fun String.cleanPath(): String {
    val rootPath = Environment.getExternalStorageDirectory().absolutePath
    return this.removePrefix(rootPath).let {
        it.ifEmpty { "/" }
    }
}
