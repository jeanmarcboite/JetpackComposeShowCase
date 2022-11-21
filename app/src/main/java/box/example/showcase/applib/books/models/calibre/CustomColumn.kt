package box.example.showcase.applib.books.models.calibre

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "custom_columns")
data class CustomColumnEntry(
    @DatabaseField
    val label: String? = null,
    @DatabaseField
    val name: String? = null,
    @DatabaseField
    val datatype: String? = null,
    @DatabaseField
    val mark_for_delete: Boolean = false,
    @DatabaseField
    val editable: Boolean = true,
    @DatabaseField
    val display: String = "{}",
    @DatabaseField
    val is_multiple: Boolean = false,
    @DatabaseField
    val normalized: Boolean = false,
) : CalibreEntity()

open class CustomColumn : CalibreEntity() {
    //    @DatabaseField(canBeNull = true)
    val book: Int = 0

    @DatabaseField
    val value: String? = null
    override fun toString(): String {
        return value ?: "null"
    }
}

open class BooksCustomColumnsLink(
    @DatabaseField
    val book: Int = 0,
    @DatabaseField
    val value: Int = 0,
) : CalibreEntity()

@DatabaseTable(tableName = "custom_column_1")
class CustomColumn1 : CustomColumn()

@DatabaseTable(tableName = "books_custom_column_1_link")
class BooksCustomColumn1Link : BooksCustomColumnsLink()

@DatabaseTable(tableName = "custom_column_2")
class CustomColumn2 : CustomColumn()

@DatabaseTable(tableName = "books_custom_column_2_link")
class BooksCustomColumn2Link : BooksCustomColumnsLink()




