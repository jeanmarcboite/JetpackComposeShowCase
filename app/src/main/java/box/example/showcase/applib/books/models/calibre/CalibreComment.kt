package box.example.showcase.applib.books.models.calibre

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "comments")
data class CalibreComment(
    @DatabaseField
    val text: String? = null,
    @DatabaseField
    val book: Int? = null,
) : CalibreEntity()
