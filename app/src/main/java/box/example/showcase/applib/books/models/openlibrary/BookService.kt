package box.example.showcase.applib.books.models.openlibrary

import retrofit2.Response

enum class BookQueryType {
    Any,
    Author, Title
}

interface BookService {
    suspend fun getBooks(
        query: String,
        type: BookQueryType = BookQueryType.Any
    ): Response<OpenLibraryBookList>

    suspend fun getBook(id: String): Response<OpenLibraryBook>
    suspend fun getBookByIsbn(isbn: String): Response<OpenLibraryBook>
}
