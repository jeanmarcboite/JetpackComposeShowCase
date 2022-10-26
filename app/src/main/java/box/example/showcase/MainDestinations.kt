package box.example.showcase

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import box.example.showcase.ui.home.HomeScreen

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

val mainScreens = listOf(Home)