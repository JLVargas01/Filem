package com.spiralsoft.filem.presentation.components

import com.spiralsoft.filem.constants.FileType
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import java.io.File

@Composable
fun FileIcon(file: File, fileType: FileType) {
    when (fileType) {
        FileType.IMAGE -> {
            // Es una imagen, usa Coil para cargar la miniatura
            val painter = rememberAsyncImagePainter(
                model =  coil3.request.ImageRequest.Builder(LocalContext.current)
                    .data(file) // Carga la imagen desde el archivo
                    .crossfade(true) // Animación de transicion
                    .placeholder(fileType.iconRes) // Muestra un icono para la carga
                    .error(fileType.errorIconRes) // Muestra un icono si hay un error
                    .memoryCacheKey(file.absolutePath) // Establece la clave de caché en el path del archivo
                    .diskCacheKey(file.absolutePath) // Establece la clave de cache de disco en el path del archivo
                    .size(128) // Tamaño de la miniatura
                    .build() // Construye la solicitud
            )
            Image(
                painter = painter,
                contentDescription = "Miniatura de imagen",
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        } else -> {
        // Otro tipo de archivo, muestra el icono
        Image(
            painter = painterResource(id = fileType.iconRes),
            contentDescription = "Ícono de archivo",
            modifier = Modifier.size(32.dp)
        )
    }

    }

}
