package com.ihardanilchanka.sampleappkmp.android.core.ui.resource

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val LightColors = lightColorScheme(
    primary = AppColor.purple500,
    primaryContainer = AppColor.purple700,
    onPrimary = AppColor.white,
    secondary = AppColor.teal200,
    secondaryContainer = AppColor.teal700,
    onSecondary = AppColor.black,
    surface = AppColor.white,
    background = AppColor.blueGray100,
)

private val DarkColors = darkColorScheme(
    primary = AppColor.purple200,
    primaryContainer = AppColor.purple700,
    onPrimary = AppColor.black,
    secondary = AppColor.teal200,
    secondaryContainer = AppColor.teal200,
    onSecondary = AppColor.black,
    surface = AppColor.black,
    background = AppColor.blueGray900,
)

val AppTypography = Typography(
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        letterSpacing = 0.25.sp
    ),
    labelSmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 1.5.sp,
        fontFeatureSettings = "c2sc, smcp"
    )
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = AppTypography,
        content = content,
    )
}
