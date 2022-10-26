package box.example.showcase

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import box.example.showcase.ui.screens.about.AboutScreen
import box.example.showcase.ui.screens.home.HomeScreen

interface Destination {
    val icon: ImageVector
    val route: String
    val screen: @Composable () -> Unit
}
object Home : Destination {
    override val icon = Icons.Filled.Home
    override val route = "home"
    override val screen: @Composable () -> Unit = { HomeScreen() }
}

object About : Destination {
    override val icon = Icons.Filled.Info
    override val route = "about"
    override val screen: @Composable () -> Unit = { AboutScreen() }
}

val mainScreens = listOf(Home, About)