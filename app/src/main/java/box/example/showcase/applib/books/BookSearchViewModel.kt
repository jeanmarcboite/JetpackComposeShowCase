package box.example.showcase.applib.books

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookSearchViewModel @Inject constructor(
    val bookService: BookService
) : ViewModel() {
    fun getBooks(query: String): Error? {
        return bookService.getBooks()
    }

}