package box.example.showcase

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import box.example.showcase.applib.notes.NotesViewModel
import box.example.showcase.applib.notes.components.NoteAction

class MainViewModel : ViewModel() {
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
}