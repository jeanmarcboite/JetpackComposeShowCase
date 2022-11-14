package box.example.showcase.applib.books

import box.example.showcase.applib.books.models.Book
import box.example.showcase.applib.books.models.BookList
import retrofit2.Response

enum class BookQueryType {
    Any,
    Author, Title
}

interface BookService {
    suspend fun getBooks(query: String, type: BookQueryType = BookQueryType.Any): Response<BookList>
    suspend fun getBook(id: String): Response<Book>
}
