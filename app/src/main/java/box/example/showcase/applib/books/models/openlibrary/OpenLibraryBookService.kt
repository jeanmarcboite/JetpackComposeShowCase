package box.example.showcase.applib.books.models.openlibrary

import retrofit2.Response

class OpenLibraryBookService : BookService {
    override suspend fun getBooks(
        query: String,
        type: BookQueryType
    ): Response<OpenLibraryBookList> {
        val api = OpenLibraryApiHelper.getInstance()

        return when (type) {
            BookQueryType.Any -> api.getBooksByQ(query)
            BookQueryType.Author -> api.getBooksByAuthor(query)
            BookQueryType.Title -> api.getBooksByTitle(query)
        }
    }

    override suspend fun getBook(id: String): Response<OpenLibraryBook> {
        return OpenLibraryApiHelper.getInstance().getBook(id)
    }

    override suspend fun getBookByIsbn(isbn: String): Response<OpenLibraryBook> {
        return OpenLibraryApiHelper.getInstance().getBookByIsbn(isbn)
    }
}

