package box.example.showcase

import android.util.Log
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import box.example.showcase.ui.Page
import box.example.showcase.ui.models.AuthViewModel

const val DARK_MODE = "dark_mode"

class MainViewModel() : ViewModel() {
    lateinit var applicationSettings: ApplicationSettings

    val darkMode = mutableStateOf(false)
    lateinit var navController: NavHostController

    @OptIn(ExperimentalMaterial3Api::class)
    lateinit var drawerState: DrawerState
    lateinit var selectedItem: MutableState<Page?>
    lateinit var pages: Map<String, Page>

    lateinit var authViewModel: AuthViewModel

    init {
        Log.v("boxy", "init MainViewModel")
    }

    fun readApplicationSettings(_applicationSettings: ApplicationSettings) {
        applicationSettings = _applicationSettings
        darkMode.value = applicationSettings[booleanPreferencesKey(DARK_MODE)] ?: false
    }

    fun setDarkMode(value: Boolean) {
        darkMode.value = value
        applicationSettings[booleanPreferencesKey(DARK_MODE)] = value
    }

    fun navigate(route: String) {
        navController.apply {
            try {
                navigate(route) {
                    //popUpTo = navController.graph.startDestination
                    launchSingleTop = true
                }
                navigateSingleTopTo(route)
                selectedItem.value =
                    pages[navController.currentDestination?.route]!!
            } catch (e: Exception) {
                Log.e("boxx", "Cannot navigate to {route}: ${e.message}"/*, e*/)
            }
        }
    }

    fun popBackStack() {
        navController.popBackStack()
        Log.w(
            "boxxx",
            navController.currentDestination.toString()
        )
    }
}