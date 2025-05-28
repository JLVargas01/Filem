/**
 * Representa un archivo(FileItem), con sus caracteristicas y las acciones que puede realizar
 * Las caracteristicas son: el icon(depende de su tipo), nombre, ruta, tamaño, y la posibilidad
 * de realizar alguna accion al hacer click(La accion aqui no esta definida)
 */
package com.spiralsoft.filem.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import java.io.File
import com.spiralsoft.filem.utils.cleanPath
import com.spiralsoft.filem.constants.FileType

@Composable
fun FileItem(
    dir: File,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val fileType = remember(dir) { FileType.fromFile(dir) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                fileType.onClickAction(context, dir)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier.size(48.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center,
                content = {
                    // Ponerle el icono al tipo de archivo
                    FileIcon(file = dir, fileType = fileType)
                }
            )

            Spacer(modifier = Modifier.width(6.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = dir.name.ifBlank { dir.absolutePath.cleanPath() },
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = dir.absolutePath.cleanPath(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Box(
                modifier = Modifier.size(24.dp),
                contentAlignment = Alignment.CenterEnd,
                content = {
                    Icon(Icons.Default.MoreVert,
                        contentDescription = "Más opciones",
                        modifier = Modifier.size(32.dp)
                    )
                }
            )

        }
    }

}

//NOTA: Organizar mejor esta funcion y colocarla en un mejor lugar
@Composable
private fun FileIcon(file: File, fileType: FileType) {
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
