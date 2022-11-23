package box.example.showcase.applib.books.models.calibre

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "custom_columns")
data class CalibreCustomColumn(
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

