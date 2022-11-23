package box.example.showcase.applib.books.models.calibre

import com.j256.ormlite.field.DatabaseField

open class CalibreEntity(
    @DatabaseField(generatedId = true)
    val id: Int = 0
) {
    override fun equals(other: Any?): Boolean {
        return other != null
                && other.javaClass == javaClass
                && id == (other as CalibreEntity).id
    }
}


open class CalibreSortableEntity(
    @DatabaseField
    val name: String? = null,
    @DatabaseField(columnName = "sort")
    val sort: String? = null
) : CalibreEntity() {
    override fun toString(): String {
        return name.toString()
    }
}
