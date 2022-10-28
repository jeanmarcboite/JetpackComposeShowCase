package box.example.showcase

import android.util.Log
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseUser

const val DARK_MODE = "dark_mode"

class MainViewModel : ViewModel() {
    lateinit var applicationSettings: ApplicationSettings
    val user: MutableState<FirebaseUser?> = mutableStateOf(null)

    val darkMode = mutableStateOf(false)
    lateinit var navController: NavHostController

    @OptIn(ExperimentalMaterial3Api::class)
    lateinit var drawerState: DrawerState

    init {
        Log.v("boxy", "init MainViewModel")
        //user.value = FirebaseAuth.getInstance().currentUser
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
        navController?.apply {
            try {
                navigate(route) {
                    //popUpTo = navController.graph.startDestination
                    launchSingleTop = true
                }
                navigateSingleTopTo(route)
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