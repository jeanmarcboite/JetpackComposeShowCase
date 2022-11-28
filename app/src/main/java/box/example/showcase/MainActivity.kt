package box.example.showcase

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.rememberNavController
import box.example.showcase.applib.notes.NotesDatabase
import box.example.showcase.applib.notes.NotesRepository
import box.example.showcase.applib.notes.NotesViewModel
import box.example.showcase.applib.notes.NotesViewModelFactory
import box.example.showcase.ui.app.ModalDrawer
import box.example.showcase.ui.app.TopBar
import box.example.showcase.ui.models.AuthViewModel
import box.example.showcase.ui.models.NavModule
import box.example.showcase.ui.pages.mainPages
import box.example.showcase.ui.pages.notes.models.FirebaseNotesViewModel
import box.example.showcase.ui.theme.ShowCaseTheme
import com.jsramraj.flags.Flags
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@HiltAndroidApp
class HiltApp : Application()

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val mainViewModel: MainViewModel by viewModels()
    val authViewModel: AuthViewModel by viewModels()
    val firebaseNotesViewModel: FirebaseNotesViewModel by viewModels()
    val notesViewModel: NotesViewModel by viewModels {
        val dao = NotesDatabase.getDatabase(this).dao()
        val repository = NotesRepository(dao)
        NotesViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.readApplicationSettings(this)
        mainViewModel.authViewModel = authViewModel
        mainViewModel.firebaseNotesViewModel = firebaseNotesViewModel
        mainViewModel.notesViewModel = notesViewModel

        setContent {
            ShowCaseTheme(mainViewModel.darkMode.value) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen() {
        val context = LocalContext.current

        Flags.init(LocalContext.current)

        Log.e("boxxxx [MainScreen]", "set NavModule")
        NavModule.pages = mainPages(context, mainViewModel)
        NavModule.navController = rememberNavController()
        NavModule.drawerState = rememberDrawerState(DrawerValue.Closed)
        NavModule.selectedPage =
            remember { mutableStateOf(NavModule.pages[context.getString(R.string.start_destination)]) }

        ModalDrawer(
            mainViewModel.snackbarHostState,
            topBar = {
                TopBar(
                    stringResource(id = R.string.app_name),
                    mainViewModel,
                )
            },
        )
    }
}

