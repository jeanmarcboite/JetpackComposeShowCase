package box.example.showcase.ui.pages.todo

data class Task(
    val id: String = "",
    val title: String = "",
    val photoUrl: String? = null,
    val timestamp: Long = 0,
    val due: Long = 0,
    val priority: String = "",
    val description: String = "",
    val url: String = "",
    val flag: Boolean = false,
    val completed: Boolean = false,
    val userId: String = ""
)

