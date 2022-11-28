package box.example.showcase.ui.app

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import box.example.showcase.ApplicationStateViewModel
import box.example.showcase.R
import box.example.showcase.ui.models.NavViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalDrawer(
    topBar: @Composable () -> Unit = {},
) {
    val navViewModel = hiltViewModel<NavViewModel>()
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
                        selected = page == navViewModel.selectedPage.value,
                        onClick = {
                            scope.launch { navViewModel.drawerState.close() }
                            //selectedPage.value = page
                            navViewModel.navigate(route)
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    ) {
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
                            composable(context.getString(page.route)) { navBackStackEntry ->
                                page.parseArguments(navBackStackEntry.arguments)
                                //page.Content()

                                Scaffold(
                                    bottomBar = {
                                        page.BottomAppBar()
                                    },
                                    content = {
                                        Column(Modifier.padding(it)) {
                                            val applicationStateViewModel =
                                                hiltViewModel<ApplicationStateViewModel>()
                                            page.Content()
                                            Spacer(modifier = Modifier.weight(1f))
                                            SnackbarHost(
                                                //modifier = Modifier.align(Alignment.BottomCenter),
                                                hostState = applicationStateViewModel.snackbarHostState,
                                                snackbar = { snackbarData: SnackbarData ->
                                                    Card(
                                                        shape = RoundedCornerShape(8.dp),
                                                        border = BorderStroke(2.dp, Color.Red),
                                                        modifier = Modifier
                                                            .padding(16.dp)
                                                            .wrapContentSize()
                                                    ) {
                                                        Snackbar(
                                                            snackbarData
                                                        )
                                                    }
                                                }
                                            )
                                        }
                                    }
                                )

                            }
                        }
                    }
                },
                floatingActionButton = { navViewModel.selectedPage.value?.floatingActionButton() },
            )
        }
    }
}
