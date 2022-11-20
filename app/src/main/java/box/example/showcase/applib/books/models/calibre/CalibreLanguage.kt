package box.example.showcase.applib.books.models.calibre

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "languages")
data class CalibreLanguage(
    @DatabaseField
    val lang_code: String? = null,
) : CalibreEntity()


@DatabaseTable(tableName = "books_languages_link")
data class CalibreBooksLanguagesLink(
    @DatabaseField
    val book: Int = 0,
    @DatabaseField
    val lang_code: Int = 0,
    @DatabaseField
    val item_order: Int = 0,
) : CalibreEntity()