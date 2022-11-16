package box.example.showcase.ui.pages.calibre

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "books",
    indices = [Index(name = "authors_idx", value = ["author_sort"]), Index(
        name = "books_idx",
        value = ["sort"]
    )]
)
data class CalibreBook(
    val author_sort: String,
    val flags: Int,
    val has_cover: Boolean,
    @PrimaryKey
    val id: Int,
    val isbn: String,
    @ColumnInfo(name = "last_modified", defaultValue = "CURRENT_TIMESTAMP")
    val last_modified: Long,
    val lccn: String,
    val path: String,
    val pubdate: Long,
    @ColumnInfo(defaultValue = "1.0")
    val series_index: Double,
    val sort: String,
    val timestamp: String,
    val title: String,
    val uuid: String
)