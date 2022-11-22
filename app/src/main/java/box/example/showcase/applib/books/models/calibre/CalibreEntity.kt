package box.example.showcase.applib.books.models.calibre

import com.j256.ormlite.field.DatabaseField

open class CalibreEntity(
    @DatabaseField(generatedId = true)
    val id: Int = 0
)