package box.example.showcase

import android.content.Context
import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.ViewModel
import box.example.showcase.applib.notes.NotesViewModel
import box.example.showcase.applib.notes.components.NoteAction
import box.example.showcase.ui.models.AuthViewModel
import box.example.showcase.ui.pages.notes.models.FirebaseNotesViewModel

class MainViewModel : ViewModel() {
    lateinit var applicationSettings: ApplicationSettings

    lateinit var DARK_MODE: String
    val darkMode = mutableStateOf(false)


    lateinit var authViewModel: AuthViewModel
    lateinit var firebaseNotesViewModel: FirebaseNotesViewModel
    lateinit var notesViewModel: NotesViewModel

    var snackbarHostState = SnackbarHostState()

    val leftSwipeToReveal = mutableStateOf(false)
    val leftSwipeAction = mutableStateOf(NoteAction.Delete)
    val leftSwipeActions = listOf(
        mutableStateOf(NoteAction.None),
        mutableStateOf(NoteAction.None)
    )
    val rightSwipeToReveal = mutableStateOf(true)
    val rightSwipeAction = mutableStateOf(NoteAction.None)
    val rightSwipeActions = listOf(
        mutableStateOf(NoteAction.Star),
        mutableStateOf(NoteAction.Undo),
        mutableStateOf(NoteAction.Delete)
    )

    init {
        Log.v("boxy", "init MainViewModel")
    }

    fun readApplicationSettings(context: Context) {
        applicationSettings = ApplicationSettings(context)

        DARK_MODE = context.resources.getString(R.string.dark_mode)
        darkMode.value = applicationSettings[booleanPreferencesKey(DARK_MODE)] ?: false
    }

    fun setDarkMode(value: Boolean) {
        darkMode.value = value
        applicationSettings[booleanPreferencesKey(DARK_MODE)] = value
    }
}