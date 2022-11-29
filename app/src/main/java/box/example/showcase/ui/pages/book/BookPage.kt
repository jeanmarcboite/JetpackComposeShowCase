package box.example.showcase.ui.pages.book

import android.util.Log
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
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

    private suspend fun search(
        openLibraryBookSearchViewModel: OpenLibraryBookSearchViewModel,
        book: CalibreBook?,
        openLibraryBook: MutableState<OpenLibraryBook?>
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
                openLibraryBook.value = result.getOrNull()
                Log.d("boxxx [OpenLibraryBook]", "$openLibraryBook")
            }
        }
    }

    @Composable
    override fun floatingActionButton() {
        val openLibraryBookSearchViewModel: OpenLibraryBookSearchViewModel = hiltViewModel()
        val bookViewModel = hiltViewModel<BookViewModel>()

        if (bookViewModel.openLibraryBook.value == null) {
            val scope = rememberCoroutineScope()
            var progressVisible by remember {
                mutableStateOf(false)
            }
            ExtendedFloatingActionButton(
                modifier = Modifier.width(150.dp),
                onClick = {
                    progressVisible = true
                    scope.launch {
                        search(
                            openLibraryBookSearchViewModel,
                            bookViewModel.calibreBook.value,
                            bookViewModel.openLibraryBook
                        )
                        progressVisible = false
                    }
                }, icon = {
                    Icon(
                        Icons.Filled.Search, contentDescription = "Favorite"
                    )
                }, text = {
                    if (progressVisible) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.Green,
                            strokeWidth = 2.dp
                        )
                    } else
                        Text(
                            text = "${bookViewModel.calibreBook.value?.title}",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                })
        }
    }
}