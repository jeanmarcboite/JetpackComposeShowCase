package box.example.showcase.applib.books

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class BookSearchViewModel @Inject constructor(
    private val bookService: BookService
) : ViewModel() {
    var bookList: MutableState<BookList?> = mutableStateOf(null)

    suspend fun getBooks(
        query: String,
        type: BookQueryType = BookQueryType.Any
    ): Result<BookList?> {
        try {
            val result: Response<BookList> = bookService.getBooks(query, type)
            Log.d("boxxxx", "result: ${result}")

            bookList.value = result.body()
            return Result.success(bookList.value)
        } catch (e: Exception) {
            Log.e("boxx", "Cannot get books from {$query}: ${e.message}"/*, e*/)

            return Result.failure(e)
        }
    }
}