package com.spiralsoft.filem.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

private val LightColors = lightColorScheme(
    primary = LightBlue80,
    onPrimary = LightBlue40,
    secondary = LightDarkBlue,
    background = LightGray,
    error = ErrorRed
)

private val DarkColors = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,
    secondary = LightBlue80,
    background = DarkBackground,
    surface = DarkSurface,
    onBackground = DarkOnBackground,
    onSurface = DarkOnBackground,
    error = ErrorRed
)

@Composable
fun FilemTheme(
    darkTheme: Boolean = true,  //El tema por defecto es oscuro
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
