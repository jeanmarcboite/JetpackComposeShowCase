package box.example.showcase.applib.books.models.calibre

class BooksLink(id: Int, val book: Int = 0, val value: String) : CalibreEntity(id) {
    override fun toString(): String = "$id: $book -> $value"
}