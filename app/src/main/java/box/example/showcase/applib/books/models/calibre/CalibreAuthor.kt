package box.example.showcase.applib.books.models.calibre

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "authors")
data class CalibreAuthor(
    @DatabaseField
    val link: String? = null,
    val books: MutableList<CalibreBook> = mutableListOf(),
) : CalibreSortableEntity()
