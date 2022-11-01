package box.example.showcase

import android.content.Context
import android.os.Bundle
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
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.rememberNavController
import box.example.showcase.ui.app.ModalDrawer
import box.example.showcase.ui.models.AuthViewModel
import box.example.showcase.ui.theme.ShowCaseTheme

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : ComponentActivity() {
    val mainViewModel: MainViewModel by viewModels()
    val authViewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.readApplicationSettings(ApplicationSettings(this))
        mainViewModel.authViewModel = authViewModel

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
        mainViewModel.pages = mainPages(context, mainViewModel)
        mainViewModel.navController = rememberNavController()
        mainViewModel.drawerState = rememberDrawerState(DrawerValue.Closed)

        mainViewModel.selectedItem =
            remember { mutableStateOf(mainViewModel.pages[context.getString(R.string.home_page_route)]) }

        ModalDrawer(mainViewModel)
    }
}

