package box.example.showcase.applib.books

import box.example.showcase.applib.books.models.Book
import box.example.showcase.applib.books.models.BookList
import retrofit2.Response

class OpenLibraryBookService : BookService {
    override suspend fun getBooks(query: String, type: BookQueryType): Response<BookList> {
        val api = OpenLibraryApiHelper.getInstance()

        return when (type) {
            BookQueryType.Any -> api.getBooksByQ(query)
            BookQueryType.Author -> api.getBooksByAuthor(query)
            BookQueryType.Title -> api.getBooksByTitle(query)
        }
    }

    override suspend fun getBook(id: String): Response<Book> {
        return OpenLibraryApiHelper.getInstance().getBook(id)
    }
}

