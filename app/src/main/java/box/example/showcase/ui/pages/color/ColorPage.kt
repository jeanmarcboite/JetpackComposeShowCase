package box.example.showcase.ui.pages.color

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import box.example.showcase.R
import box.example.showcase.ui.Page
import box.example.showcase.ui.components.TabBar
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
    override val screens = listOf(ColorMapScreen, ColorThemeScreen)

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(openDrawer: () -> Unit) {
        Log.d("boxx", "ColorPage")
        val context = LocalContext.current
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        // Fetch your currentDestination:
        val currentDestination = currentBackStack?.destination
        val currentScreen =
            screens.find { context.getString(it.route) == currentDestination?.route }
                ?: ColorMapScreen
        Scaffold(
            bottomBar = {
                BottomAppBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                ) {
                    TabBar(
                        screens,
                        currentScreen = currentScreen
                    ) { newScreen ->
                        navController.navigateSingleTopTo(context.getString(newScreen.route))
                    }
                }
            },
            content = {
                ScreenContent(navController, ColorMapScreen.route, Modifier.padding(it))
            })
    }
}
