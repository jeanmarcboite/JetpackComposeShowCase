package box.example.showcase.applib.books.models.openlibrary

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class OpenLibraryBookSearchViewModel @Inject constructor(
    private val bookService: BookService
) : ViewModel() {
    var openLibraryBookList: MutableState<OpenLibraryBookList?> = mutableStateOf(null)

    suspend fun getBooks(
        query: String,
        type: BookQueryType = BookQueryType.Any
    ): Result<OpenLibraryBookList?> {
        try {
            val result: Response<OpenLibraryBookList> = bookService.getBooks(query, type)
            Log.d("boxxxx", "result: ${result}")
            Log.v("boxxxx", " found: ${result.body()}")

            openLibraryBookList.value = result.body()
            return Result.success(openLibraryBookList.value)
        } catch (e: Exception) {
            Log.e("boxx", "OpenLibrary: Cannot get books from {$query}: ${e.message}"/*, e*/)

            return Result.failure(e)
        }
    }

    suspend fun getBook(
        bookID: String
    ): Result<OpenLibraryBook?> {
        try {
            val result: Response<OpenLibraryBook> = bookService.getBook(bookID)
            Log.d("boxxxx", "result: ${result}")

            return Result.success(result.body())
        } catch (e: Exception) {
            Log.e("boxx", "OpenLibrary: Cannot get book {$bookID}: ${e.message}"/*, e*/)

            return Result.failure(e)
        }
    }

    suspend fun getBookByIsbn(
        bookID: String
    ): Result<OpenLibraryBook?> {
        var error: Exception?
        try {
            val result: Response<OpenLibraryBook> = bookService.getBookByIsbn(bookID)
            Log.d("boxxxx", "result: ${result}")

            if (result.code() == 200)
                return Result.success(result.body())

            error = Exception("error ${result.code()} ${result.message()}")
        } catch (e: Exception) {
            error = e
        }

        Log.e("boxx", "OpenLibrary: Cannot get book $bookID: ${error!!.message}"/*, e*/)

        return Result.failure(error)
    }
}