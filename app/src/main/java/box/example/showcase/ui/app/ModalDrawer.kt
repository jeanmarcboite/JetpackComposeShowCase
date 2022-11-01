package box.example.showcase.ui.app

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import box.example.showcase.MainViewModel
import box.example.showcase.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalDrawer(mainViewModel: MainViewModel) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

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
