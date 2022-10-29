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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(mainViewModel: MainViewModel) {
    val context = LocalContext.current
    mainViewModel.navController = rememberNavController()

    mainViewModel.drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    mainViewModel.pages = mainPages(context, mainViewModel)

    Log.d("boxxx", "Pages: " + mainViewModel.pages.toString())
    mainViewModel.selectedItem =
        remember { mutableStateOf(mainViewModel.pages[context.getString(R.string.home_page_route)]) }

    ModalNavigationDrawer(
        drawerState = mainViewModel.drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                mainViewModel.pages.values.filter { it.showInDrawer() }.forEach { page ->
                    val route = stringResource(id = page.route)
                    val title = stringResource(id = page.title)
                    NavigationDrawerItem(
                        icon = { Icon(imageVector = page.icon, contentDescription = null) },
                        label = { Text(title) },
                        selected = page == mainViewModel.selectedItem.value,
                        onClick = {
                            scope.launch { mainViewModel.drawerState.close() }
                            //selectedItem.value = page
                            mainViewModel.navigate(route)
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
                            mainViewModel.selectedItem.value?.buttonIcon,
                            onButtonClicked = {
                                scope.launch {
                                    mainViewModel.selectedItem.value?.onButtonClicked()
                                    mainViewModel.selectedItem.value =
                                        mainViewModel.pages[mainViewModel.navController.currentDestination?.route]!!
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
                            startDestination = context.getString(R.string.home_page_route)
                        ) {
                            mainViewModel.pages.values.forEach { page ->
                                composable(context.getString(page.route)) {
                                    page.Content {

                                    }
                                }
                            }
                        }
                    },
                    bottomBar = {
                        BottomAppBar(containerColor = MaterialTheme.colorScheme.primary) {
                            mainViewModel.selectedItem.value?.route?.let {
                                Text(context.getString(it))
                            }
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
