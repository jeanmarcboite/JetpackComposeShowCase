package box.example.showcase.ui.pages.lab

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.util.*

@DatabaseTable(tableName = "lab_notes")
data class LabNote(
    @DatabaseField(columnName = "ID", generatedId = true)
    val id: UUID = UUID.randomUUID(),

    @DatabaseField(canBeNull = false)
    var rank: Int = 0,

    @DatabaseField
    var title: String = "",

    @DatabaseField
    var timestamp: Date = Date()
)

@DatabaseTable(tableName = "other_notes")
data class LabNote2(
    @DatabaseField(columnName = "ID", generatedId = true)
    val id: UUID = UUID.randomUUID(),

    @DatabaseField(columnName = "rankOrNull", canBeNull = true)
    var rank: Int? = null,

    @DatabaseField
    var title: String = "",

    @DatabaseField
    var timestamp: Date = Date()
)