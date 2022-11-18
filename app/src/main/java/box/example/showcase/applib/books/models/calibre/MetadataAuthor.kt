package box.example.showcase.applib.books.models.calibre

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "authors")
data class MetadataAuthor(
    @DatabaseField(generatedId = true)
    val id: Int = 0,
    @DatabaseField
    val name: String? = null,
    @DatabaseField
    val sort: String? = null,
    @DatabaseField
    val link: String? = null,
    )
