package box.example.showcase.applib.books.models.calibre

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "books_authors_link")
data class BooksAuthorsLink(
    @DatabaseField
    val author: Int = 0,
    @DatabaseField
    val book: Int = 0,
) : CalibreEntity()