package box.example.showcase

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.ViewModel
import box.example.showcase.ui.models.AuthViewModel
import box.example.showcase.ui.models.NavViewModel
import box.example.showcase.ui.pages.notes.models.NotesViewModel

const val DARK_MODE = "dark_mode"

class MainViewModel() : ViewModel() {
    lateinit var applicationSettings: ApplicationSettings

    val darkMode = mutableStateOf(false)


    lateinit var authViewModel: AuthViewModel
    lateinit var navViewModel: NavViewModel
    lateinit var notesViewModel: NotesViewModel

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