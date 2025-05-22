package com.spiralsoft.filem.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import com.spiralsoft.filem.utils.cleanPath
import com.spiralsoft.filem.constants.FileType
import com.spiralsoft.filem.utils.FileOpener
import java.io.File

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
                FileOpener.openFile(context, dir)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = fileType.iconRes),
                contentDescription = "Ícono de archivo",
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

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
        }
    }

}
