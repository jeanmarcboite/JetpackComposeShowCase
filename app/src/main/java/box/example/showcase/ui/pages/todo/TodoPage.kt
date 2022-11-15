package box.example.showcase.ui.pages.todo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import box.example.showcase.R
import box.example.showcase.ui.Page
import box.example.showcase.ui.pages.todo.models.Todo
import box.example.showcase.ui.pages.todo.models.TodoViewModel
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Eye

class TodoPage :
    Page(
        FontAwesomeIcons.Solid.Eye,
        R.string.todo_page_route,
        R.string.todo_page_title
    ) {
    private lateinit var todoViewModel: TodoViewModel

    init {
    }

    @Composable
    override fun Content(openDrawer: () -> Unit) {
        val todoDao = TodoDatabase.getDatabase(LocalContext.current).todoDao()
        val repository = TodoRepository(todoDao)
        todoViewModel = TodoViewModel(repository)
        todoViewModel.insert(Todo(title = "todo"))
        Column(modifier = Modifier.fillMaxSize()) {
            Text("Todo")
        }
    }
}