package com.spiralsoft.filem.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.MaterialTheme

private val LightColors = lightColorScheme(
    primary = LightBlue80,
    onPrimary = LightBlue40,
    secondary = LightDarkBlue,
    background = LightGray,
//    surface = DarkSurface,
//   onBackground = DarkOnBackground,
//    onSurface = DarkOnBackground,
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
    isDarkTheme: Boolean = true,  //El tema por defecto es oscuro
    contentApp: @Composable () -> Unit
) {

    val colorsApp: ColorScheme = if (isDarkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colorsApp,
        shapes = ShapesApp,
        typography = TypographyApp,
        content = contentApp
    )
}
