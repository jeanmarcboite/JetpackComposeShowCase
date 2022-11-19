package box.example.showcase.ui.pages.database

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.documentfile.provider.DocumentFile
import androidx.navigation.NavDestination
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
import box.example.showcase.ui.pages.ColorMapScreen
import box.example.showcase.ui.pages.color.ColorThemeScreen
import box.example.showcase.ui.pages.database.components.View
import box.example.showcase.ui.theme.margin_half
import com.j256.ormlite.android.apptools.OpenHelperManager
import com.j256.ormlite.dao.GenericRawResults
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.brands.Whatsapp
import kotlinx.coroutines.launch
import java.io.FileOutputStream
import java.io.OutputStream


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

        val tabs = listOf(ColorMapScreen, ColorThemeScreen)
        // Fetch your currentDestination:
        val currentDestination: NavDestination? = currentBackStack?.destination

        val selected = true
        val color = MaterialTheme.colorScheme.onSurface
        val durationMillis =
            if (selected) TabFadeInAnimationDuration else TabFadeOutAnimationDuration
        val animSpec = remember {
            tween<Color>(
                durationMillis = durationMillis,
                easing = LinearEasing,
                delayMillis = TabFadeInAnimationDelay
            )
        }
        val tabTintColor by animateColorAsState(
            targetValue = if (selected) color else color.copy(alpha = InactiveTabOpacity),
            animationSpec = animSpec
        )
        Scaffold(bottomBar = {
            BottomAppBar(
                actions = {
                    tabs.forEach { screen ->
                        IconAction(
                            selected = true, text = context.getString(screen.title),
                            icon = screen.icon
                        ) {
                            navController.navigateSingleTopTo(context.getString(screen.route))
                        }
                    }
                    IconAction(
                        selected = true, text = context.getString(ColorMapScreen.title),
                        icon = ColorMapScreen.icon
                    ) {
                        navController.navigateSingleTopTo(context.getString(ColorMapScreen.route))
                    }
                    IconButton(onClick = {
                        navController.navigateSingleTopTo(context.getString(ColorMapScreen.route))
                    }) {
                        Icon(ColorMapScreen.icon, contentDescription = "Localized description")
                    }
                    IconButton(onClick = {
                        navController.navigateSingleTopTo(context.getString(ColorThemeScreen.route))
                    }) {
                        Row {
                            Icon(
                                ColorThemeScreen.icon,
                                contentDescription = "Localized description",
                            )
                        }

                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { /* do something */ },
                        containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                    ) {
                        Icon(Icons.Filled.Add, "Localized description")
                    }
                }
            )
        }) {
            NavHost(
                navController = navController,
                startDestination = context.getString(ColorMapScreen.route),
                modifier = Modifier.padding(it)
            ) {
                composable(route = context.getString(ColorMapScreen.route)) {
                    ColorMapScreen.content()
                }
                composable(route = context.getString(ColorThemeScreen.route)) {
                    ColorThemeScreen.content()

                }
            }
        }
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    fun CContent() {
        val scroll = rememberScrollState()
        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current
        val listDocumentFile = remember { mutableStateOf<List<DocumentFile>?>(null) }
        val listBook = remember { mutableStateOf<List<MetadataBook>?>(null) }
        val listAuthor = remember { mutableStateOf<List<MetadataAuthor>?>(null) }

        val launcher =
            rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
                it?.run {
                    copyDatabase(context, this)
                }
            }

        val directoryLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.OpenDocumentTree()) {
                it?.run {
                    val tree = DocumentFile.fromTreeUri(context, this)
                    val metadata: DocumentFile? = tree?.listFiles()?.find { documentFile ->
                        documentFile.name == DATABASE_NAME
                    }
                    if (metadata?.isFile == true) {
                        copyDatabase(context, metadata.uri)
                    }

                }
            }

        Column {
            Button(modifier = Modifier.fillMaxWidth(),
                onClick = {
                    launcher.launch("application/octet-stream")
                }) {
                Text("copy database")
            }

            Button(modifier = Modifier.fillMaxWidth(),
                onClick = {
                    directoryLauncher.launch(null)
                }) {
                Text("copy calibre library database")
            }

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
                    val list: MutableList<MetadataBook> = dao.queryForAll()
                    Log.d("boxxx [ormlite]", "list of ${list.size} books")
                    listBook.value = list

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

            listBook.value?.apply {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(top = margin_half)
                ) {
                    items(listBook.value!!) {
                        it.View()
                    }
                }
            }

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

    fun copyDatabase(context: Context, input: Uri) {
        databaseAvailable.value = false
        val item = context.contentResolver.openInputStream(input)
        val bytes: ByteArray? = item?.readBytes()
        item?.close()

        bytes?.run {
            val output: OutputStream = FileOutputStream(context.getDatabasePath(DATABASE_NAME))
            output.write(this, 0, size)
            output.close()
            databaseAvailable.value = true
        }
    }
}

