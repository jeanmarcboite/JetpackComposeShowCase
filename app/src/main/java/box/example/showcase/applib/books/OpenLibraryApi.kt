package box.example.showcase.applib.books

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
http://openlibrary.org/search.json?q=the+lord+of+the+rings
http://openlibrary.org/search.json?title=the+lord+of+the+rings
http://openlibrary.org/search.json?author=tolkien
http://openlibrary.org/search.json?q=the+lord+of+the+rings&page=2
http://openlibrary.org/search/authors.json?q=twain
 **/

interface OpenLibraryApi {
    @GET("search.json")
    suspend fun getBooksByTitle(@Query("title") query: String): Response<BookList>

    @GET("search.json")
    suspend fun getBooksByQ(@Query("q") query: String): Response<BookList>

    @GET("search.json")
    suspend fun getBooksByAuthor(@Query("author") query: String): Response<BookList>
}
