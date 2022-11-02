package box.example.showcase.ui.pages.notes.models

import java.util.*

data class Note(
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
    val userId: String = "",
    val star: Boolean = false,
    val archived: Boolean = false,
    val deleted: Boolean = false
)

