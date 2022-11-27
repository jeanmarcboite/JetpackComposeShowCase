package box.example.showcase.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import box.example.showcase.ui.components.TabBar
import box.example.showcase.ui.navigation.Tab
import box.example.showcase.ui.navigation.navigateSingleTopTo

abstract class TabPage(
    icon: ImageVector,
    @StringRes route: Int,
    @StringRes title: Int,
    buttonIcon: ImageVector = Icons.Default.Menu,
) : Page(icon, route, title, buttonIcon) {
    override val tabs: List<Tab> = listOf()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        // Fetch your currentDestination:
        val currentDestination: NavDestination? = currentBackStack?.destination
        if (tabs.isNotEmpty())
            Scaffold(
                bottomBar = {
                    BottomAppBar(
                        containerColor = MaterialTheme.colorScheme.surface,
                    ) {
                        TabBar(
                            tabs,
                            currentTab = tabs.findRoute(context, currentDestination?.route)
                        ) { newScreen ->
                            navController.navigateSingleTopTo(context.getString(newScreen.route))
                        }
                    }
                },
                content = {
                    NavHost(
                        navController = navController,
                        startDestination = context.getString(tabs.first().route),
                        modifier = Modifier.padding(it)
                    ) {
                        tabs.forEach { screen ->
                            composable(context.getString(screen.route)) {
                                screen.Content()
                            }
                        }
                    }
                })
    }

    // The bottomAppBar is already in the content, since we need the navController (we could also use a viewModel
    @Composable
    override fun BottomAppBar() {
    }

    fun List<Tab>.findRoute(context: Context, route: String?): Tab {
        if (route == null)
            return first()
        return find { context.getString(it.route) == route } ?: first()
    }
}
