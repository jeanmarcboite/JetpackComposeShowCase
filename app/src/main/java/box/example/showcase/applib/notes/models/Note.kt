package box.example.showcase.applib.notes.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    @ColumnInfo(name = "photo_url")
    val photoUrl: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
    val due: Long = 0,
    val priority: String = "",
    val description: String = "",
    val url: String = "",
    val flag: Boolean = false,
    val completed: Boolean = false,
    @ColumnInfo(name = "user_id")
    val userId: String = "",
    val star: Boolean = false,
    val archived: Boolean = false,
    val deleted: Boolean = false
)

