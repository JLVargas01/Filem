/**
 * Representa un directorio(DirectoryItem), con sus caracteristicas y la accion que puede realizar
 * Las caracteristicas son: icono, nombre, ruta, tamaño y la accion de navegacion hacia el contenido
 * de ese directorio
 */
package com.spiralsoft.filem.presentation.components

import com.spiralsoft.filem.R
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.combinedClickable
import java.nio.file.Path

@Composable
fun DirectoryItem(
    pathDir: Path,
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
                    Image(
                        painter = painterResource(id = R.drawable.icon_folder0),
                        contentDescription = "Ícono de carpeta",
                        modifier = Modifier.size(32.dp)
                    )
                }
            )

            Spacer(modifier = Modifier.width(6.dp))
            // Información del directorio
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = pathDir.fileName.toString(),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = pathDir.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Box(
                modifier = Modifier.size(24.dp),
                contentAlignment = Alignment.CenterEnd,
                content = {
                    Image(
                        painter = painterResource(id = R.drawable.arrow_forward0),
                        modifier = Modifier.size(16.dp),
                        contentDescription = "Navegar"
                    )
                }
            )

        }
    }
}
