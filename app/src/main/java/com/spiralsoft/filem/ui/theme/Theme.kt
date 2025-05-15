package com.spiralsoft.filem.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = LightBlue40,
    onPrimary = Color.White,
    secondary = LightDarkBlue,
    background = LightGray,
    surface = Color.White,
    error = ErrorRed,
    onBackground = Color.Black,
    onSurface = Color.Black
)

private val DarkColors = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,
    secondary = LightBlue80,
    background = DarkBackground,
    surface = DarkSurface,
    error = ErrorRed,
    onBackground = DarkOnBackground,
    onSurface = DarkOnBackground
)

@Composable
fun FilemTheme(
    useDarkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colors = if (useDarkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}
