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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.documentfile.provider.DocumentFile
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import box.example.showcase.R
import box.example.showcase.applib.books.models.calibre.MetadataAuthor
import box.example.showcase.applib.books.models.calibre.MetadataBook
import box.example.showcase.applib.books.models.calibre.MetadataDatabaseHelper
import box.example.showcase.ui.Page
import box.example.showcase.ui.components.IconAction
import box.example.showcase.ui.navigation.navigateSingleTopTo
import box.example.showcase.ui.pages.database.components.LauncherButton
import com.j256.ormlite.android.apptools.OpenHelperManager
import com.j256.ormlite.dao.GenericRawResults
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.brands.Whatsapp
import kotlinx.coroutines.launch


const val DATABASE_NAME = "metadata.db"
const val DATABASE_VERSION = 25
private val TabHeight = 56.dp
private const val InactiveTabOpacity = 0.60f

private const val TabFadeInAnimationDuration = 150
private const val TabFadeInAnimationDelay = 100
private const val TabFadeOutAnimationDuration = 100

interface Screen {
    val icon: ImageVector
    val route: Int
    val title: Int
    val content: @Composable () -> Unit
}

class DatabasePage :
    Page(
        FontAwesomeIcons.Brands.Whatsapp,
        R.string.database_page_route,
        R.string.database_page_title
    ) {
    val databaseAvailable = mutableStateOf(false)

    @Composable
    override fun bottomAppBar() {
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val listBook = remember { mutableStateOf<List<MetadataBook>?>(null) }
        val listAuthor = remember { mutableStateOf<List<MetadataAuthor>?>(null) }


        val tabs = listOf(BooksScreen(listBook), AuthorsScreen(listAuthor))
        // Fetch your currentDestination:
        val currentRoute = currentBackStack?.destination?.route
        getDatabase(listBook, listAuthor)
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
                    LauncherButton(
                        databaseAvailable
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
                        screen.content()
                    }
                }
            }
        }
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    fun getDatabase(
        listBook: MutableState<List<MetadataBook>?>,
        listAuthor: MutableState<List<MetadataAuthor>?>
    ) {
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()

        if (databaseAvailable.value) {
            val dbHelper =
                OpenHelperManager.getHelper(context, MetadataDatabaseHelper::class.java)
            if (dbHelper.isOpen) {
                Log.d("boxxxx", "database ${dbHelper.databaseName} is open")
            }
            try {
                val dao = dbHelper.getDao(MetadataBook::class.java)
                // Don't kow why sqlite_schema does not work (sqlite version too old?)
                val query: GenericRawResults<Array<String>> =
                    dao.queryRaw("SELECT name FROM sqlite_master WHERE type = 'table'")
                val results = query.results.map {
                    it.toList()
                }
                Log.d("boxxxx", "tables ${results}")
                //val list: MutableList<MetadataBook> = dao.queryForAll()
                //Log.d("boxxx [ormlite]", "list of ${list.size} books")
                listBook.value = dbHelper.getDao(MetadataBook::class.java).queryForAll()
                listAuthor.value = dbHelper.getDao(MetadataAuthor::class.java).queryForAll()

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
                e.printStackTrace()
                coroutineScope.launch {
                    mainViewModel.snackbarHostState.showSnackbar(
                        errorMessage,
                        withDismissAction = true,
                        duration = SnackbarDuration.Indefinite
                    )
                }
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

