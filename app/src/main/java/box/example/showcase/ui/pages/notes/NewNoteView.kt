package box.example.showcase.ui.pages.notes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import box.example.showcase.ui.pages.notes.models.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewNoteView(
    modifier: Modifier = Modifier,
    onButtonClick: (Note) -> Unit
) {
    val note = remember {
        mutableStateOf(Note())
    }
    Column {
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            value = note.value.title,
            label = { Text("Title") },
            colors = TextFieldDefaults.outlinedTextFieldColors(),
            onValueChange = {
                note.value = note.value.copy(title = it)
            })
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            value = note.value.description,
            label = { Text("Description") },
            colors = TextFieldDefaults.outlinedTextFieldColors(),
            onValueChange = {
                note.value = note.value.copy(description = it)
            })
        Button(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            onClick = { onButtonClick(note.value) }) {
            Text("Add new note")
        }

    }
}
