package box.example.showcase.applib.books.models.openlibrary

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
http://openlibrary.org/search.json?q=the+lord+of+the+rings
http://openlibrary.org/search.json?title=the+lord+of+the+rings
http://openlibrary.org/search.json?author=tolkien
http://openlibrary.org/search.json?q=the+lord+of+the+rings&page=2
http://openlibrary.org/search/authors.json?q=twain

https://openlibrary.org/works/OL45883W.json
https://openlibrary.org/books/OL7353617M.json
https://openlibrary.org/authors/OL26320A.json
https://openlibrary.org/isbn/9780140328721.json


https://covers.openlibrary.org/b/olid/OL7440033M-S.jpg
 **/

interface OpenLibraryApi {
    @GET("search.json")
    suspend fun getBooksByTitle(@Query("title") query: String): Response<OpenLibraryBookList>

    @GET("search.json")
    suspend fun getBooksByQ(@Query("q") query: String): Response<OpenLibraryBookList>

    @GET("search.json")
    suspend fun getBooksByAuthor(@Query("author") query: String): Response<OpenLibraryBookList>

    @GET("books/{id}.json")
    suspend fun getBook(@Path("id") id: String): Response<OpenLibraryBook>

    @GET("isbn/{isbn}.json")
    suspend fun getBookByIsbn(@Path("isbn") id: String): Response<OpenLibraryBook>

    @GET("authors/{id}.json")
    suspend fun getAuthor(@Path("id") id: String): Response<OpenLibraryAuthor>
}
