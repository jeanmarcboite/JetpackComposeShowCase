package box.example.showcase.ui.pages.todo

import java.util.*

data class Task(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val photoUrl: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
    val due: Long = 0,
    val priority: String = "",
    val description: String = "",
    val url: String = "",
    val flag: Boolean = false,
    val completed: Boolean = false,
    val userId: String = ""
)

