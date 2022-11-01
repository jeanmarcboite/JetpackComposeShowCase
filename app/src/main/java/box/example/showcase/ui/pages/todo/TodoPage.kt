package box.example.showcase.ui.pages.todo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
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
    @Composable
    override fun Content(openDrawer: () -> Unit) {
        val viewModel = mainViewModel.todoViewModel
        Surface(color = MaterialTheme.colorScheme.background) {
            Column {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(items = viewModel.tasks.value) {
                        TaskView(it)
                    }

                }
            }
        }

    }


    @Composable
    override fun floatingActionButton() {
        ExtendedFloatingActionButton(
            onClick = {
                mainViewModel.todoViewModel.tasks.value =
                    listOf(Task(title = "how", description = "how todo"))
            },
            icon = {
                Icon(
                    Icons.Filled.AddCircle,
                    contentDescription = "Favorite"
                )
            },
            text = { Text("Add Todo") }
        )
    }

}
