package com.spiralsoft.filem.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.spiralsoft.filem.R
import java.io.File

@Composable
fun FileItem(
    file: File,
    onClick: () -> Unit
) {

    val iconRes = if (file.isDirectory)
        R.drawable.icon_folder0
    else R.drawable.icon_file0

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        tonalElevation = 2.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = "Carpeta",
                modifier = Modifier
                    .size(32.dp)
                    .padding(end = 12.dp),
                tint = Color.Unspecified
            )
            Text(
                text = file.name,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
