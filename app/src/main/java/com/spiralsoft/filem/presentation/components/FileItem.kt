/**
 * Representa un archivo(FileItem), con sus caracteristicas y las acciones que puede realizar
 * Las caracteristicas son: el icon(depende de su tipo), nombre, ruta, tamaño, y la posibilidad
 * de realizar alguna accion al hacer click(La accion aqui no esta definida)
 */
package com.spiralsoft.filem.presentation.components

import com.spiralsoft.filem.constants.FileType
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import java.nio.file.Path

@Composable
fun FileItem(
    pathFile: Path,
    fileType: FileType,
    isSelected: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Check de selección
            AnimatedCheck(isSelected)
            // Ícono de carpeta
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center,
                content = {
                    // Ponerle el icono al tipo de archivo
                    FileIcon(file = pathFile.toFile(), fileType = fileType)
                }
            )

            Spacer(modifier = Modifier.width(6.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = pathFile.fileName.toString(),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = pathFile.toString(),
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
