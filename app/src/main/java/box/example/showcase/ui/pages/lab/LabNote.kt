package box.example.showcase.ui.pages.lab

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.util.*

@DatabaseTable(tableName = "sample_table")
data class LabNote(
    @DatabaseField(columnName = "ID", generatedId = true)
    val id: UUID = UUID.randomUUID(),

    @DatabaseField(canBeNull = false)
    var numField: Int = 3,

    @DatabaseField
    var title: String = "",

    @DatabaseField
    var timestamp: Date = Date()
)