package box.example.showcase.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

interface Route {
    val icon: ImageVector
    val route: String
    val screen: @Composable () -> Unit
}