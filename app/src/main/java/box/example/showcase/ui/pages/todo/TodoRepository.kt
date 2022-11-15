package box.example.showcase.ui.pages.todo

import androidx.lifecycle.LiveData
import box.example.showcase.ui.pages.todo.models.Todo
import box.example.showcase.ui.pages.todo.models.TodoDao

class TodoRepository(private val todoDao: TodoDao) {

    val readAllData: LiveData<List<Todo>> = todoDao.getAll()

    suspend fun insert(todo: Todo) {
        todoDao.insert(todo)
    }

    suspend fun update(todoItem: Todo) {
        todoDao.update(todoItem)
    }

    suspend fun delete(todo: Todo) {
        todoDao.delete(todo)
    }

    suspend fun deleteAll() {
        todoDao.deleteAll()
    }
}