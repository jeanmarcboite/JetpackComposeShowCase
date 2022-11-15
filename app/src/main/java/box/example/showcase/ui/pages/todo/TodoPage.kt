package box.example.showcase.ui.pages.todo

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import box.example.showcase.R
import box.example.showcase.ui.Page
import box.example.showcase.ui.pages.notes.NoteListView
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Eye

class TodoPage :
    Page(
        FontAwesomeIcons.Solid.Eye,
        R.string.todo_page_route,
        R.string.todo_page_title
    ) {
    @Composable
    override fun Content(openDrawer: () -> Unit) {
        val viewModel = mainViewModel.notesViewModel
        //mainViewModel.notesViewModel.insert(Note(title = "todo"))
        LaunchedEffect(key1 = true) {

        }
        Surface(color = MaterialTheme.colorScheme.background) {
            NoteListView(notes = viewModel.notes) {
                viewModel.insert(it)
            }
        }
    }
}