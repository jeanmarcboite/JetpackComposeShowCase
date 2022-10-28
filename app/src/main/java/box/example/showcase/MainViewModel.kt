package box.example.showcase

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseUser

const val DARK_MODE = "dark_mode"

class MainViewModel : ViewModel() {
    lateinit var applicationSettings: ApplicationSettings
    var navController: NavHostController? = null
    val user: MutableState<FirebaseUser?> = mutableStateOf(null)

    val darkMode = mutableStateOf(false)

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
                navigateSingleTopTo(route)
            } catch (e: Exception) {
                Log.e("boxx", "Cannot navigate to {route}: ${e.message}"/*, e*/)
            }
        }
    }
}
