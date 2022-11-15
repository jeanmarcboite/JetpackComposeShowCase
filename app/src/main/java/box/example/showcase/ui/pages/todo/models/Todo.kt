package box.example.showcase.ui.pages.todo.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class Todo(

    @PrimaryKey(autoGenerate = true)
    var ID: Long = 0L,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo
    var completed: Boolean = false
)
