package box.example.showcase.ui.pages.notes

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import box.example.showcase.R
import box.example.showcase.applib.notes.components.NewNoteView
import box.example.showcase.applib.notes.components.NoteListView
import box.example.showcase.ui.Page
import box.example.showcase.ui.pages.notes.models.FirebaseNotesViewModel
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Feather

class NotesPage :
    Page(
        FontAwesomeIcons.Solid.Feather,
        R.string.notes_page_route,
        R.string.notes_page_title
    ) {
    lateinit var noteEdit: MutableState<Boolean>

    @Composable
    override fun Content() {
        val notesRoot = stringResource(R.string.notes_root)
        noteEdit = remember { mutableStateOf(false) }
        val viewModel = hiltViewModel<FirebaseNotesViewModel>()

        LaunchedEffect(true) {
            viewModel.setRoot(notesRoot)
        }

        if (!noteEdit.value) {
            Surface(color = MaterialTheme.colorScheme.background) {
                NoteListView(notes = viewModel.notes) {
                    viewModel.add(notesRoot, it)
                }
            }
        } else {
            NewNoteView {
                if (it != null)
                    viewModel.add(notesRoot, it)
                noteEdit.value = false
            }
        }

    }

    @SuppressLint("ComposableNaming")
    @Composable
    override fun floatingActionButton() {
        ExtendedFloatingActionButton(
            onClick = {
                noteEdit.value = true
            },
            icon = {
                Icon(
                    Icons.Filled.AddCircle,
                    contentDescription = "Favorite"
                )
            },
            text = { Text(stringResource(R.string.add_notes)) }
        )
    }

}
