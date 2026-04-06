package nl.ramonvanschalm.toepen.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val ColorScheme = lightColorScheme(
    primary = Maroon,
    onPrimary = White,
    primaryContainer = MaroonLight,
    onPrimaryContainer = White,
    secondary = Gold,
    onSecondary = MaroonDark,
    background = Beige,
    onBackground = MaroonDark,
    surface = Beige,
    onSurface = MaroonDark,
    surfaceVariant = BeigeDark,
    onSurfaceVariant = Maroon
)

@Composable
fun ToepTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ColorScheme,
        typography = Typography,
        content = content
    )
}
