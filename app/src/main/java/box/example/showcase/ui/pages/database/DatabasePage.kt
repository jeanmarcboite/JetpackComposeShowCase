package box.example.showcase.ui.pages.database

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.documentfile.provider.DocumentFile
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import box.example.showcase.ApplicationStateViewModel
import box.example.showcase.R
import box.example.showcase.applib.books.models.calibre.CalibreAuthor
import box.example.showcase.applib.books.models.calibre.CalibreBook
import box.example.showcase.applib.books.models.calibre.CalibreDatabase
import box.example.showcase.ui.Page
import box.example.showcase.ui.components.IconAction
import box.example.showcase.ui.models.CalibreDatabaseViewModel
import box.example.showcase.ui.navigation.navigateSingleTopTo
import box.example.showcase.ui.pages.database.components.DatabaseSelection
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.brands.Whatsapp

object DatabasePage :
    Page(
        FontAwesomeIcons.Brands.Whatsapp,
        R.string.database_page_route,
        R.string.database_page_title
    ) {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel = hiltViewModel<CalibreDatabaseViewModel>()
        val context = LocalContext.current
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()

        val tabs = listOf(
            BooksScreen(
                viewModel.database.value?.books?.value?.sortedBy {
                    val book = it as CalibreBook
                    book.sort ?: book.title
                }),
            AuthorsScreen(viewModel.database.value?.authors?.value?.sortedBy {
                val author = it as CalibreAuthor
                author.sort ?: author.name
            })
        )
        // Fetch your currentDestination:
        val currentRoute = currentBackStack?.destination?.route
        getDatabase(viewModel)
        Scaffold(bottomBar = {
            BottomAppBar(
                actions = {
                    tabs.forEach { screen ->
                        IconAction(
                            Modifier.padding(16.dp),
                            selected = context.getString(screen.route) == currentRoute,
                            text = context.getString(screen.title),
                            icon = screen.icon
                        ) {
                            navController.navigateSingleTopTo(context.getString(screen.route))
                        }
                    }
                },
                floatingActionButton = {
                    DatabaseSelection(
                        viewModel.version
                    )
                }
            )
        }) {
            NavHost(
                navController = navController,
                startDestination = context.getString(R.string.calibre_book_table_route),
                modifier = Modifier.padding(it)
            ) {
                tabs.forEach { screen ->
                    composable(context.getString(screen.route)) {
                        screen.Content()
                    }
                }
            }
        }
    }

    @SuppressLint("ComposableNaming")
    @Composable
    fun getDatabase(
        viewModel: CalibreDatabaseViewModel
    ) {
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
        }
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    fun CContent() {
        val scroll = rememberScrollState()
        val listDocumentFile = remember { mutableStateOf<List<DocumentFile>?>(null) }
        Column {
            listDocumentFile.value?.run {
                Column(modifier = Modifier.verticalScroll(scroll)) {
                    forEach {
                        Card(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                        ) {
                            Row {
                                Text(it.name.toString())
                                if (it.type != null)
                                    Text(
                                        "[${it.type.toString()}]",
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                            }
                        }
                    }
                }
            }
        }
    }
}

