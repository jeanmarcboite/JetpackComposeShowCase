package box.example.showcase.ui.pages.todo

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import box.example.showcase.R
import box.example.showcase.ui.Page
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Feather

class TodoPage() :
    Page(
        FontAwesomeIcons.Solid.Feather,
        R.string.todo_page_route,
        R.string.todo_page_title
    ) {
    lateinit var taskEdit: MutableState<Boolean>

    @Composable
    override fun Content(openDrawer: () -> Unit) {
        taskEdit = remember { mutableStateOf(false) }
        val viewModel = mainViewModel.todoViewModel

        if (!taskEdit.value) {
            Surface(color = MaterialTheme.colorScheme.background) {
                TaskListView(tasks = viewModel.tasks)
            }
        } else {
            NewTaskView() {
                viewModel.tasks.add(it)
                taskEdit.value = false
            }
        }

    }

    @Composable
    override fun floatingActionButton() {
        ExtendedFloatingActionButton(
            onClick = {
                taskEdit.value = true
            },
            icon = {
                Icon(
                    Icons.Filled.AddCircle,
                    contentDescription = "Favorite"
                )
            },
            text = { Text(stringResource(R.string.add_todo)) }
        )
    }

}
