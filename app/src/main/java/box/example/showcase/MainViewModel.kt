package box.example.showcase

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController

const val DARK_MODE = "dark_mode"

class MainViewModel : ViewModel() {
    lateinit var applicationSettings: ApplicationSettings
    var navController: NavHostController? = null

    val darkMode = mutableStateOf(false)

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
}
