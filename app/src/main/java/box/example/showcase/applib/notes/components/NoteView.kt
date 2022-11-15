package box.example.showcase.applib.notes.components

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import box.example.showcase.applib.notes.models.Note

enum class NoteAction {
    None, Star, Archive, Undo, Delete
}

@Composable
fun NoteView(note: Note, modifier: Modifier = Modifier, onNoteChanged: (Note) -> Unit) {
    val offset = remember {
        mutableStateOf(0f)
    }

    val swiped = remember {
        mutableStateOf(false)
    }

    Box(modifier.fillMaxWidth()) {
        NoteBackground(
            note,
            listOf(NoteAction.Star, NoteAction.Undo),
            listOf(NoteAction.Delete),
            offset,
            swiped,
            onNoteChanged
        )
        NoteForeground(
            note, offset, swiped,
            onClick = {
                Log.d("boxx", "...click ${note}")
            }, onNoteChanged
        )
    }
}
