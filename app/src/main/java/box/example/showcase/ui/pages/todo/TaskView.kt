package box.example.showcase.ui.pages.todo

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun TaskView(task: Task) {
    Text(task.description)
}