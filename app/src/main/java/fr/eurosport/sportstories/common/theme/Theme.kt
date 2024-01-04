package fr.eurosport.sportstories.common.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

private val AppLightColorScheme = lightColorScheme(
    primary = ColorPrimary,
    onPrimary = Color.Gray,
    secondary = Color.LightGray,
    onSecondary = Color.Black,
    error = Red800
)

private val AppDarkColorScheme = darkColorScheme(
    primary = ColorPrimaryDark,
    onPrimary = Color.Gray,
    secondary = Color.Black,
    onSecondary = Color.White,
    error = Red800
)

@Composable
fun SportStoriesTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (SportStoriesThemeSettings.isInDarkTheme.value) AppDarkColorScheme else AppLightColorScheme,
        content = content
    )
}


object SportStoriesThemeSettings {
    var isInDarkTheme: MutableState<Boolean> = mutableStateOf(false)
}