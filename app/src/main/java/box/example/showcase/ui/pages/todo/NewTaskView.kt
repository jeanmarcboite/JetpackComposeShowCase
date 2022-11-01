package box.example.showcase.ui.pages.todo

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTaskView(
    modifier: Modifier = Modifier,
    onButtonClick: (Task) -> Unit
) {
    val task = remember {
        mutableStateOf(Task())
    }
    Column {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            value = task.value.title,
            label = { Text("Title") },
            colors = TextFieldDefaults.outlinedTextFieldColors(),
            onValueChange = {
                task.value = task.value.copy(title = it)
            })
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            value = task.value.description,
            label = { Text("Description") },
            colors = TextFieldDefaults.outlinedTextFieldColors(),
            onValueChange = {
                task.value = task.value.copy(description = it)
            })
        Button(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            onClick = { onButtonClick(task.value) }) {
            Text("Add new task")
        }

    }
}
