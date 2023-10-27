package fr.eurosport.sportstories.common.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

private val LightThemeColors = lightColors(
    primary = ColorPrimary,
    primaryVariant = ColorPrimaryDark,
    onPrimary = Color.Gray,
    secondary = Color.LightGray,
    secondaryVariant = ColorPrimaryDark,
    onSecondary = Color.Black,
    error = Red800
)

private val DarkThemeColors = darkColors(
    primary = ColorPrimaryDark,
    primaryVariant = ColorPrimary,
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
      colors = if (SportStoriesThemeSettings.isInDarkTheme.value) DarkThemeColors else LightThemeColors,
      content = content
  )
}


object SportStoriesThemeSettings {
  var isInDarkTheme: MutableState<Boolean> = mutableStateOf(false)
}