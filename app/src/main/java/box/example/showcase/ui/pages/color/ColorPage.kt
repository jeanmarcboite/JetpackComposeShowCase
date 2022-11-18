package box.example.showcase.ui.pages.color

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import box.example.showcase.R
import box.example.showcase.applib.bored.BoredViewModel
import box.example.showcase.ui.Page
import box.example.showcase.ui.components.TabBar
import box.example.showcase.ui.navigation.Screen
import box.example.showcase.ui.navigation.ScreenContent
import box.example.showcase.ui.navigation.navigateSingleTopTo
import box.example.showcase.ui.pages.ColorMapScreen
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Cocktail

class ColorPage : Page(
    FontAwesomeIcons.Solid.Cocktail,
    R.string.color_page_route,
    R.string.color_page_title
) {
    override val screens = listOf(ColorMapScreen, ColorThemeScreen)

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val boredViewModel: BoredViewModel = hiltViewModel()
        //val boredViewModel: BoredViewModel = viewModel()
        val context = LocalContext.current
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        // Fetch your currentDestination:
        val currentDestination: NavDestination? = currentBackStack?.destination
        Scaffold(
            bottomBar = {
                BottomAppBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                ) {
                    //Text(boredViewModel.msg())
                    Text(
                        "Content: ${boredViewModel.msg()}",
                        modifier = Modifier.padding(end = 32.dp)
                    )
                    TabBar(
                        screens,
                        currentScreen = screens.findRoute(context, currentDestination?.route)
                    ) { newScreen ->
                        navController.navigateSingleTopTo(context.getString(newScreen.route))
                    }
                }
            },
            content = {
                ScreenContent(navController, ColorMapScreen.route, Modifier.padding(it))
            })
    }

    // The bottomAppBar is already inthe content, since we need the navController (we could also use a viewModel
    @Composable
    override fun bottomAppBar() {
    }
}

fun List<Screen>.findRoute(context: Context, route: String?): Screen {
    if (route == null)
        return ColorThemeScreen
    return find { context.getString(it.route) == route } ?: ColorThemeScreen
}
