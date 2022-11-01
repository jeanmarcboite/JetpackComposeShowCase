package box.example.showcase.ui.pages.todo

import androidx.lifecycle.ViewModel

class TodoViewModel() : ViewModel() {
    //val tasks = mutableStateOf<List<Task>>(listOf())
    val tasks = mutableListOf<Task>()
}