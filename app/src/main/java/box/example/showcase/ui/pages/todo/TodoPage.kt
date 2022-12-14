package box.example.showcase.ui.pages.todo

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import box.example.showcase.R
import box.example.showcase.applib.notes.NotesViewModel
import box.example.showcase.applib.notes.components.NewNoteView
import box.example.showcase.applib.notes.components.NoteListView
import box.example.showcase.ui.Page
import compose.icons.TablerIcons
import compose.icons.tablericons.CaretRight

class TodoPage :
    Page(
        TablerIcons.CaretRight,
        R.string.todo_page_route,
        R.string.todo_page_title
    ) {
    lateinit var noteEdit: MutableState<Boolean>

    @Composable
    override fun Content() {
        noteEdit = remember { mutableStateOf(false) }
        val viewModel = hiltViewModel<NotesViewModel>()
        LaunchedEffect(key1 = true) {

        }
        if (!noteEdit.value) {
            Surface(color = MaterialTheme.colorScheme.background) {
                NoteListView(notes = viewModel.notes) {
                    viewModel.update(it)
                }
            }
        } else {
            NewNoteView {
                if (it != null)
                    viewModel.insert(it)
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