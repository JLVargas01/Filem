package com.spiralsoft.filem.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val TypographyApp = Typography(
/*
    displayLarge = TextStyle(),

    displayMedium = TextStyle(),

    displaySmall = TextStyle(),

    headlineLarge = TextStyle(),

    headlineMedium = TextStyle(),

    headlineSmall = TextStyle(),
*/
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
/*
    titleMedium = TextStyle(),

    titleSmall = TextStyle(),
*/
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
/*
    bodySmall = TextStyle(),

    labelMedium = TextStyle(),

    labelLarge = TextStyle(),
*/
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),

)
