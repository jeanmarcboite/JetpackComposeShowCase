package box.example.showcase.ui.pages.book

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import box.example.showcase.R
import box.example.showcase.applib.books.models.BookViewModel
import box.example.showcase.applib.books.models.calibre.CalibreBook
import box.example.showcase.applib.books.models.openlibrary.BookQueryType
import box.example.showcase.applib.books.models.openlibrary.OpenLibraryBook
import box.example.showcase.applib.books.models.openlibrary.OpenLibraryBookList
import box.example.showcase.applib.books.models.openlibrary.OpenLibraryBookSearchViewModel
import box.example.showcase.ui.TabbedPage
import box.example.showcase.ui.models.NavViewModel
import box.example.showcase.ui.pages.calibre.CalibreBookTab
import box.example.showcase.ui.pages.calibre.OpenLibraryBookTab
import compose.icons.TablerIcons
import compose.icons.tablericons.Book
import kotlinx.coroutines.launch

class BookPage : TabbedPage(
    listOf(CalibreBookTab, OpenLibraryBookTab),
    TablerIcons.Book,
    R.string.book_page_route,
    R.string.book_page_title,
    Icons.Default.ArrowBack
) {
    override fun showInDrawer() = false

    override suspend fun onButtonClicked(navViewModel: NavViewModel) {
        navViewModel.navController.popBackStack()
    }

    suspend fun search(
        openLibraryBookSearchViewModel: OpenLibraryBookSearchViewModel,
        book: CalibreBook?
    ) {
        if (book != null) {
            var result: Result<OpenLibraryBook?>? = null

            val isbn = book.ISBN
            if (isbn != null) {
                result = openLibraryBookSearchViewModel.getBookByIsbn(
                    isbn
                )
            }
            if ((result == null) || result.isFailure) {
                // OpenLibraryBookList(docs=[], numFound=0, numFoundExact=true, num_found=0, offset=null, q=, start=0)
                val resultBookList: Result<OpenLibraryBookList?> =
                    openLibraryBookSearchViewModel.getBooks(
                        book.title!!, BookQueryType.Title
                    )
            } else {
                val openLibraryBook: OpenLibraryBook? = result.getOrNull()
                Log.d("boxxx [OpenLibraryBook]", "$openLibraryBook")
            }
        }
    }

    @Composable
    override fun floatingActionButton() {
        val openLibraryBookSearchViewModel: OpenLibraryBookSearchViewModel = hiltViewModel()
        val book = hiltViewModel<BookViewModel>().calibreBook.value
        val scope = rememberCoroutineScope()
        ExtendedFloatingActionButton(onClick = {
            scope.launch {
                search(openLibraryBookSearchViewModel, book)
            }
        }, icon = {
            Icon(
                Icons.Filled.Search, contentDescription = "Favorite"
            )
        }, text = {
            Text("${book?.title}")
        })
    }
}