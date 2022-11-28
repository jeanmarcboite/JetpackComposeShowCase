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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import box.example.showcase.applib.notes.NotesDatabase
import box.example.showcase.applib.notes.NotesModule
import box.example.showcase.applib.notes.NotesRepository
import box.example.showcase.ui.app.ModalDrawer
import box.example.showcase.ui.app.TopBar
import box.example.showcase.ui.models.NavModule
import box.example.showcase.ui.pages.mainPages
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ApplicationModule.readApplicationSettings(this)

        val dao = NotesDatabase.getDatabase(this).dao()
        NotesModule.repository.value = NotesRepository(dao)

        setContent {
            MainScreen()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen() {
        val appViewModel = hiltViewModel<ApplicationViewModel>()
        val context = LocalContext.current

        Flags.init(LocalContext.current)

        Log.e("boxxxx [MainScreen]", "set NavModule")
        NavModule.pages = mainPages(context, mainViewModel)
        NavModule.navController = rememberNavController()
        NavModule.drawerState = rememberDrawerState(DrawerValue.Closed)
        NavModule.selectedPage =
            remember { mutableStateOf(NavModule.pages[context.getString(R.string.start_destination)]) }

        ShowCaseTheme(appViewModel.applicationSettings.darkMode.value) {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
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
    }
}

