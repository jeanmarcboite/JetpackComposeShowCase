package box.example.showcase.applib.books.models.calibre

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "data")
data class CalibreData(
    @DatabaseField
    val format: String? = null,
    @DatabaseField
    val uncompressed_size: Int = 0,
    @DatabaseField
    val name: String? = null
) : CalibreBookData()

@DatabaseTable(tableName = "feeds")
data class CalibreFeeds(
    @DatabaseField
    val title: String? = null,
    @DatabaseField
    val script: String? = null
) : CalibreEntity()

@DatabaseTable(tableName = "identifiers")
data class CalibreIdentifiers(
    @DatabaseField
    val type: String? = null,
    @DatabaseField(columnName = "val")
    val value: String? = null
) : CalibreBookData() {
    override fun toString(): String {
        return "$type:$value"
    }
}

@DatabaseTable(tableName = "library_id")
data class CalibreLibraryId(
    @DatabaseField
    val uuid: String? = null,
) : CalibreEntity()

@DatabaseTable(tableName = "preferences")
data class CalibrePreferences(
    val key: String? = null,
    @DatabaseField(columnName = "val")
    val value: String? = null
) : CalibreEntity()

@DatabaseTable(tableName = "publishers")
class CalibrePublishers() : CalibreSortableEntity()

@DatabaseTable(tableName = "ratings")
data class CalibreRating(
    @DatabaseField
    val rating: Int = 0
) : CalibreEntity()

@DatabaseTable(tableName = "series")
class CalibreSeries() : CalibreSortableEntity()

@DatabaseTable(tableName = "tags")
class CalibreTag() : CalibreNamedEntity()
