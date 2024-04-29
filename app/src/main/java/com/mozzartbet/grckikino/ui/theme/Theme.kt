package com.mozzartbet.grckikino.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
    primary = Purple900,
    primaryVariant = Purple900,
    secondary = Orange400,
    background = LightGray100,
    surface = Color.White,
    error = Red900,
    onPrimary = Color.White,
    onSecondary = Gray900,
    onBackground = Gray900,
    onSurface = Gray900
)

private val DarkColorPalette = darkColors(
    primary = Blue600,
    primaryVariant = GrayBlue900,
    secondary = Orange600,
    background = Gray900,
    surface = Gray800,
    error = Red400,
    onPrimary = Color.White,
    onSecondary = Gray900,
    onBackground = Gray500,
    onSurface = Gray500
)

@Composable
fun MyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        content = content
    )
}