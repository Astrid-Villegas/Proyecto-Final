package com.example.loginsigo.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// 1. Configuración para MODO OSCURO (opcional, por si acaso)
private val DarkColorScheme = darkColorScheme(
    primary = SigoGreen,       // Usamos tu verde
    secondary = SigoGreen,
    tertiary = SigoGreenLight
)

// 2. Configuración para MODO CLARO (El importante para tu diseño)
private val LightColorScheme = lightColorScheme(
    primary = SigoGreen,       // Aquí conectamos el color principal
    secondary = SigoGreen,
    tertiary = SigoGreenLight,
    background = Color.White,  // Forzamos fondo blanco
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = SigoGreen
)

@Composable
fun LoginSIGOTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // 3. CAMBIO IMPORTANTE: Ponemos dynamicColor en FALSE para que respete tu verde
    dynamicColor: Boolean = false,
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

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}