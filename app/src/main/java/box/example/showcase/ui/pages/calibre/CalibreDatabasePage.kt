package box.example.showcase.ui.pages.calibre

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import box.example.showcase.ApplicationStateViewModel
import box.example.showcase.R
import box.example.showcase.applib.books.models.calibre.CalibreAuthor
import box.example.showcase.applib.books.models.calibre.CalibreBook
import box.example.showcase.applib.books.models.calibre.CalibreDatabase
import box.example.showcase.ui.TabbedPage
import box.example.showcase.ui.components.TabBar
import box.example.showcase.ui.findRoute
import box.example.showcase.ui.models.CalibreDatabaseViewModel
import box.example.showcase.ui.navigation.navigateSingleTopTo
import box.example.showcase.ui.pages.calibre.components.CalibreDatabaseSelection
import compose.icons.TablerIcons
import compose.icons.tablericons.Database

object CalibreDatabasePage :
    TabbedPage(
        listOf(CalibreBooksTab, CalibreAuthorsTab),
        TablerIcons.Database,
        R.string.database_page_route,
        R.string.database_page_title
    ) {
    override val floatingActionButton: @Composable () -> Unit = {
        CalibreDatabaseSelection()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val TAG = "boxxx [CalibreDatabasePage]"
        getDatabase()
/*
        floatingActionButton = {
                    CalibreDatabaseSelection(
                        viewModel.version
                    )
                }
        */
        Log.d(TAG, "Content()")
        if (false)
            super.Content()
        else {
            val context = LocalContext.current
            val navController = rememberNavController()
            val currentBackStack by navController.currentBackStackEntryAsState()
            // Fetch your currentDestination:
            val currentDestination: NavDestination? = currentBackStack?.destination
            if (tabs.isNotEmpty())
                Scaffold(
                    floatingActionButton = floatingActionButton,
                    bottomBar = {
                        Log.d(TAG, "BottomAppBar()")
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
                        Log.d(TAG, "NavHost()")
                        NavHost(
                            navController = navController,
                            startDestination = context.getString(tabs.first().route),
                            modifier = Modifier.padding(it)
                        ) {
                            tabs.forEach { screen ->
                                Log.d(TAG, "tab ${screen.title}")
                                composable(context.getString(screen.route)) {
                                    screen.Content()
                                }
                            }
                        }
                    })
        }
    }

    @SuppressLint("ComposableNaming")
    @Composable
    fun getDatabase(
    ) {
        val viewModel: CalibreDatabaseViewModel = hiltViewModel()
        val context = LocalContext.current
        val snackbarHostState = hiltViewModel<ApplicationStateViewModel>().snackbarHostState
        LaunchedEffect(viewModel.version.value) {
            try {
                viewModel.database.value = CalibreDatabase(context)
            } catch (e: Exception) {
                val errorMessage = if (e.message == null) {
                    ""
                } else {
                    val err = StringBuilder()
                    err.append(e.message)
                    var ecause = e.cause
                    while (ecause != null) {
                        err.append(": ")
                        err.append(ecause.message)
                        ecause = ecause.cause
                    }
                    err.toString()
                }
                Log.e("boxxx [readDatabase]", errorMessage)
                // e.printStackTrace()

                snackbarHostState.showSnackbar(
                    errorMessage,
                    withDismissAction = true,
                    duration = SnackbarDuration.Indefinite
                )
            }
            viewModel.database.value?.books?.value?.sortedBy {
                val book = it as CalibreBook
                book.sort ?: book.title
            }?.apply {
                CalibreBooksTab.list.value = this
            }
            viewModel.database.value?.authors?.value?.sortedBy {
                val author = it as CalibreAuthor
                author.sort ?: author.name
            }?.apply {
                CalibreAuthorsTab.list.value = this
            }

        }
    }

}