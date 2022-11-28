package box.example.showcase

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import box.example.showcase.applib.SettingsPreferences
import box.example.showcase.applib.SettingsStore
import box.example.showcase.applib.notes.components.NoteAction

val preferences = SettingsPreferences(
    booleans = mapOf(R.string.dark_mode to true),
)

class ApplicationSettings(context: Context) : SettingsStore(context, preferences) {
    val darkMode = mutableStateOf(true)
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
}