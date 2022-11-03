package box.example.showcase.ui.pages.color

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import box.example.showcase.R
import box.example.showcase.ui.Page
import box.example.showcase.ui.components.TabRow
import box.example.showcase.ui.navigation.ScreenContent
import box.example.showcase.ui.navigation.navigateSingleTopTo
import box.example.showcase.ui.pages.ColorMapScreen
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Cocktail

class ColorPage() : Page(
    FontAwesomeIcons.Solid.Cocktail,
    R.string.color_page_route,
    R.string.color_page_title
) {
    override val tabs = listOf(ColorMapScreen, ColorThemeScreen)

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(openDrawer: () -> Unit) {
        val context = LocalContext.current
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        // Fetch your currentDestination:
        val currentDestination = currentBackStack?.destination
        val currentScreen =
            tabs.find { context.getString(it.route) == currentDestination?.route } ?: ColorMapScreen
        Scaffold(
            bottomBar = {
                TabRow(
                    tabs,
                    currentTab = currentScreen
                ) { newScreen ->
                    navController.navigateSingleTopTo(context.getString(newScreen.route))
                }
            },
            content = {
                ScreenContent(navController, ColorMapScreen.route, Modifier.padding(it))
                ScreenContent(navController, ColorThemeScreen.route, Modifier.padding(it))
            })
    }
}
