package box.example.showcase.applib.books.models.calibre

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "authors")
data class CalibreAuthor(
    @DatabaseField
    val name: String? = null,
    @DatabaseField
    val sort: String? = null,
    @DatabaseField
    val link: String? = null,
) : CalibreEntity()
