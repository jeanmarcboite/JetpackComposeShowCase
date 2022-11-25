package box.example.showcase.applib.books.models.calibre

import androidx.compose.ui.graphics.Color
import com.google.gson.Gson
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

val stringToColorMap = mapOf("green" to Color.Green, "blue" to Color.Blue, "red" to Color.Red)

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

    ) : CalibreEntity() {
    val colorMap: Map<String, Color> by lazy {
        val gson = Gson()
        val enumerationDisplay = gson.fromJson(display, EnumerationDisplay::class.java)
        val enumColors = enumerationDisplay.enum_colors.map { stringToColorMap[it] ?: Color.Red }

        val map: Map<String, Color> = enumerationDisplay.enum_values.zip(enumColors).toMap()
        map
    }
}

class EnumerationDisplay {
    val enum_values: List<String> = listOf()
    val enum_colors: List<String> = listOf()
}

