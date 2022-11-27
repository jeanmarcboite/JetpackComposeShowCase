package box.example.showcase.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

interface Tab {
    val icon: ImageVector
    val route: Int
    val title: Int
    val Content: @Composable () -> Unit
}

