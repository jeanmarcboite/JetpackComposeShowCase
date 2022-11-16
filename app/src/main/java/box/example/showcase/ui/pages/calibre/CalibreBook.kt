package box.example.showcase.ui.pages.calibre

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class CalibreBook(
    val author_sort: String,
    val flags: Int,
    val has_cover: Int,
    @PrimaryKey
    val id: Int,
    val isbn: String,
    val last_modified: String,
    val lccn: String,
    val path: String,
    val pubdate: String,
    val series_index: Double,
    val sort: String,
    val timestamp: String,
    val title: String,
    val uuid: String
)