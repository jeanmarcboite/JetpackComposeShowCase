package box.example.showcase.ui.pages.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import box.example.showcase.R
import box.example.showcase.ui.navigation.Tab
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Home


object HomeTab : Tab {
    override val icon = FontAwesomeIcons.Solid.Home
    override val route = R.string.home_tab_route
    override val title = R.string.home_tab_title
    override val Content: @Composable () -> Unit = {
        Text("home")
    }
}
