package box.example.showcase.ui.app

import android.annotation.SuppressLint
import android.util.Log
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
import box.example.showcase.R
import box.example.showcase.ui.models.NavViewModel
import box.example.showcase.ui.pages.bored.ActivityCard
import box.example.showcase.ui.pages.bored.nextActivity
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalDrawer(
    navViewModel: NavViewModel,
    topBar: @Composable () -> Unit = {},
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    ModalNavigationDrawer(
        drawerState = navViewModel.drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                navViewModel.pages.values.filter { it.showInDrawer() }.forEach { page ->
                    val route = stringResource(id = page.route)
                    val title = stringResource(id = page.title)
                    NavigationDrawerItem(
                        icon = { Icon(imageVector = page.icon, contentDescription = null) },
                        label = { Text(title) },
                        selected = page == navViewModel.selectedItem.value,
                        onClick = {
                            scope.launch { navViewModel.drawerState.close() }
                            //selectedItem.value = page
                            navViewModel.navigate(route)
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
                    topBar = topBar,
                    content = { paddingValues ->
                        NavHost(
                            modifier = Modifier
                                .padding(paddingValues)
                                .fillMaxSize(),
                            navController = navViewModel.navController,
                            startDestination = context.getString(R.string.start_destination)
                        ) {
                            navViewModel.pages.values.forEach { page ->
                                composable(context.getString(page.route)) {
                                    page.parseArguments(it.arguments)
                                    page.Content {
                                    }

                                    Scaffold(
                                        bottomBar = {
                                            BottomAppBar(
                                                containerColor = MaterialTheme.colorScheme.surface,
                                            ) {
                                                Text(
                                                    "Route: ${navViewModel.navController.currentDestination?.route}",
                                                    modifier = Modifier.padding(start = 32.dp)
                                                )
                                            }
                                        },
                                        content = {
                                            page.Content {

                                            }
                                        }
                                    )
                                }
                            }
                        }
                    },
                    floatingActionButton = { navViewModel.selectedItem.value?.floatingActionButton() },
                )
            }
        }
    )
}
