package box.example.showcase.applib.books.models.calibre

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "data")
data class CalibreData(
    val book: Int = 0,
    val format: String? = null,
    val uncompressed_size: Int = 0,
    val name: String? = null
) : CalibreEntity()

@DatabaseTable(tableName = "feeds")
data class CalibreFeeds(
    val title: String? = null,
    val script: String? = null
) : CalibreEntity()

@DatabaseTable(tableName = "identifiers")
data class CalibreIdentifiers(
    val book: Int = 0,
    val type: String? = null,
    @DatabaseField(columnName = "val")
    val value: String? = null
) : CalibreEntity()

@DatabaseTable(tableName = "library_id")
data class CalibreLibraryId(
    val uuid: String? = null,
) : CalibreEntity()

@DatabaseTable(tableName = "preferences")
data class CalibrePreferences(
    val key: String? = null,
    @DatabaseField(columnName = "val")
    val value: String? = null
) : CalibreEntity()

@DatabaseTable(tableName = "publishers")
class publishers() : CalibreSortableEntity()

@DatabaseTable(tableName = "ratings")
data class CalibreRating(
    val rating: Int = 0
) : CalibreEntity()

@DatabaseTable(tableName = "series")
class series() : CalibreSortableEntity()

@DatabaseTable(tableName = "tags")
data class CalibreTag(
    val name: String? = null
) : CalibreEntity()
