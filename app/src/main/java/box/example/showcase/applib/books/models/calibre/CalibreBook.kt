package box.example.showcase.applib.books.models.calibre

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.util.*

@DatabaseTable(tableName = "books")
data class CalibreBook(
    @DatabaseField
    val author_sort: String = "",
    val authors: MutableList<CalibreAuthor> = mutableListOf(),
    var comment: String? = null,
    val columns: MutableMap<String, MutableList<CalibreEntity>> = mutableMapOf(),
    val customColumns: MutableMap<CalibreCustomColumn, MutableList<String>> = mutableMapOf(),
    val languages: MutableList<String> = mutableListOf(),
    @DatabaseField
    val flags: Int = 0,
    @DatabaseField
    val has_cover: Boolean = false,
    @DatabaseField
    val isbn: String? = null,
//@ColumnInfo(name = "last_modified", defaultValue = "CURRENT_TIMESTAMP")
    @DatabaseField
    val last_modified: String? = null,

    @DatabaseField
    val lccn: String? = null,

    @DatabaseField
    val path: String? = null,

    @DatabaseField
    val pubdate: String? = null,

//@ColumnInfo(defaultValue = "1.0")
    @DatabaseField
    val series_index: Double? = null,

    @DatabaseField
    val sort: String? = null,

    @DatabaseField(format = "yyyy-MM-dd HH:mm:ss")
    val timestamp: Date? = null,

    @DatabaseField
    val title: String? = null,

    @DatabaseField
    val uuid: String? = null
) : CalibreEntity() {
    val ISBN: String?
        get() {
            return if (isbn != null && isbn != "")
                isbn
            else if (columns["identifiers"] != null) {
                val column = columns["identifiers"]?.find {
                    (it as CalibreIdentifiers).type == "isbn"
                }
                if (column != null)
                    (column as CalibreIdentifiers).value else null
            } else
                null
        }
}

fun CalibreBook?.toString(): String {
    return if (this != null) title.toString() else "null"
}

