package box.example.showcase.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

abstract class Tab(
    val icon: ImageVector,
    val route: Int,
    val title: Int
) {
    @Composable
    abstract fun Content()
}

