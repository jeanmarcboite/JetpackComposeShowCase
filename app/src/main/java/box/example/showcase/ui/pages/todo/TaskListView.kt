package box.example.showcase.ui.pages.todo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TaskListView(tasks: List<Task>, onTask: (Task) -> Unit) {
    Column {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(items = tasks) {
                TaskView(it, modifier = Modifier.padding(vertical = 8.dp)) {
                    onTask(it)
                }
            }
        }
    }
}
