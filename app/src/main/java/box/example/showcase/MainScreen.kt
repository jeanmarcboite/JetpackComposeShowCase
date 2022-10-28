package box.example.showcase

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import box.example.showcase.ui.app.TopBar
import box.example.showcase.ui.pages.about.AboutPage
import box.example.showcase.ui.pages.bored.BoredPage
import box.example.showcase.ui.pages.home.HomePage
import box.example.showcase.ui.pages.login.LoginPage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(mainViewModel: MainViewModel) {
    mainViewModel.navController = rememberNavController()
    mainViewModel.drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
// icons to mimic drawer destinations
    val pages = listOf(
        HomePage(mainViewModel),
        AboutPage(mainViewModel),
        BoredPage(mainViewModel),
        LoginPage(mainViewModel)
    )
    val routes = pages.associateBy { stringResource(id = it.route) }
    Log.d("boxxx", routes.toString())
    val context = LocalContext.current
    val selectedItem = remember { mutableStateOf(pages[0]) }

    ModalNavigationDrawer(
        drawerState = mainViewModel.drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                pages.forEach { page ->
                    val route = stringResource(id = page.route)
                    NavigationDrawerItem(
                        icon = { Icon(imageVector = page.icon, contentDescription = null) },
                        label = { Text(route) },
                        selected = page == selectedItem.value,
                        onClick = {
                            scope.launch { mainViewModel.drawerState.close() }
                            //selectedItem.value = page
                            mainViewModel.navigate(route)
                            selectedItem.value =
                                routes[mainViewModel.navController.currentDestination?.route]!!
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Scaffold(
                    topBar = {
                        TopBar(
                            stringResource(id = R.string.app_name),
                            mainViewModel,
                            selectedItem.value.buttonIcon,
                            onButtonClicked = {
                                scope.launch {
                                    selectedItem.value.onButtonClicked()
                                    selectedItem.value =
                                        routes[mainViewModel.navController.currentDestination?.route]!!
                                }
                            }
                        )
                    },
                    content = { paddingValues ->
                        NavHost(
                            modifier = Modifier
                                .padding(paddingValues)
                                .fillMaxSize(),
                            navController = mainViewModel.navController,
                            startDestination = context.getString(pages[0].route)
                        ) {
                            pages.forEach { page ->
                                composable(context.getString(page.route)) {
                                    page.Content {

                                    }
                                }
                            }
                        }
                    },
                    bottomBar = {
                        BottomAppBar(containerColor = MaterialTheme.colorScheme.primary) {
                            Text(context.getString(selectedItem.value.route))
                        }
                    }
                )
            }
        }
    )
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
