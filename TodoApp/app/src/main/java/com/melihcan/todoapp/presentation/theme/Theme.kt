package com.melihcan.todoapp.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xff05BD7A),
    secondary = Color(0xff2B2E31),
    tertiary = Pink40,
    background = Color(0xFF0E0F10),
    surface = Color(0xFFFFFFFF),
    onPrimary = Color.White,
    onSecondary = Color(0xff4D525B),
    onTertiary = Color.White,
    onBackground = Color(0xFF131416),
    onSurface = Color(0xFF1C1B1F),
    error = Color(0xffCD5037)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xff00A86B),
    secondary = Color(0xffD3D9D9),
    tertiary = Pink40,
    background = Color(0xFFFFFFFF),
    surface = Color(0xFF000000),
    onPrimary = Color.White,
    onSecondary = Color(0xff7E8491),
    onTertiary = Color.White,
    onBackground = Color(0xFFF4F6F6),
    onSurface = Color(0xFF1C1B1F),
    error = Color(0xffF44725)
)

@Composable
fun TodoAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = DarkColorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = TodoTypo,
        content = content
    )
}