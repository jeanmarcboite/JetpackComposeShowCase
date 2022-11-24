package box.example.showcase.applib.books.models.calibre

import com.j256.ormlite.field.DatabaseField

open class CalibreEntity(
    @DatabaseField(generatedId = true)
    val id: Int = 0
) {
    override fun hashCode(): Int {
        return id
    }

    override fun equals(other: Any?): Boolean {
        return other != null
                && other.javaClass == javaClass
                && id == (other as CalibreEntity).id
    }
}


open class CalibreNamedEntity(
    @DatabaseField
    val name: String? = null,
) : CalibreEntity() {
    override fun toString(): String {
        return name.toString()
    }
}

open class CalibreSortableEntity(
    @DatabaseField(columnName = "sort")
    val sort: String? = null
) : CalibreNamedEntity() {
    override fun toString(): String {
        return name.toString()
    }
}

open class CalibreBookData(
    @DatabaseField
    val book: Int = 0,
) : CalibreEntity()
