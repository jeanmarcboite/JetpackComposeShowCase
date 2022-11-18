package box.example.showcase.applib.books.models.calibre

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.util.*

/*
@Entity(
    tableName = "books",
    indices = [Index(name = "authors_idx", value = ["author_sort"]), Index(
        name = "books_idx",
        value = ["sort"]
    )]
)

 */

@DatabaseTable(tableName = "books")
data class MetadataBook(
    @DatabaseField(generatedId = true)
    val id: Int,
    val author_sort: String,
    val flags: Int,
    val has_cover: Boolean,
    val isbn: String,
    //@ColumnInfo(name = "last_modified", defaultValue = "CURRENT_TIMESTAMP")
    val last_modified: Long,
    val lccn: String,
    val path: String,
    val pubdate: Long,
    //@ColumnInfo(defaultValue = "1.0")
    val series_index: Double,
    val sort: String,
    val timestamp: Date,
    val title: String,
    val uuid: String
)
