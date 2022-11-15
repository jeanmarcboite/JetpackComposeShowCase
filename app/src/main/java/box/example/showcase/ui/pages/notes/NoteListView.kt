package box.example.showcase.ui.pages.notes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import box.example.showcase.applib.notes.models.Note

@Composable
fun NoteListView(notes: List<Note>, onNote: (Note) -> Unit) {
    Column {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(items = notes) {
                NoteView(it, modifier = Modifier.padding(vertical = 8.dp)) {
                    onNote(it)
                }
            }
        }
    }
}
