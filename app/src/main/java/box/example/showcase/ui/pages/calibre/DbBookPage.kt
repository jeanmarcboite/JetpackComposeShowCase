package box.example.showcase.ui.pages.calibre

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import box.example.showcase.R
import box.example.showcase.applib.books.components.calibre.ViewDetails
import box.example.showcase.applib.books.models.BookViewModel
import box.example.showcase.applib.books.models.calibre.CalibreBook
import box.example.showcase.applib.books.models.openlibrary.BookQueryType
import box.example.showcase.applib.books.models.openlibrary.OpenLibraryBook
import box.example.showcase.applib.books.models.openlibrary.OpenLibraryBookList
import box.example.showcase.applib.books.models.openlibrary.OpenLibraryBookSearchViewModel
import box.example.showcase.ui.Page
import box.example.showcase.ui.models.NavViewModel
import compose.icons.TablerIcons
import compose.icons.tablericons.Book
import kotlinx.coroutines.launch

class DbBookPage : Page(
    TablerIcons.Book,
    R.string.db_book_page_route,
    R.string.openlibrary_book_page_title,
    Icons.Default.ArrowBack,
    //arguments = listOf(navArgument("bookID") { type = NavType.StringType })
) {
    var bookID = ""
    override fun showInDrawer() = false
    override fun parseArguments(arguments: Bundle?) {
        Log.v("boxxx", "parse arguments: $arguments")
        bookID = arguments?.getString("bookID") ?: ""
    }

    override suspend fun onButtonClicked(navViewModel: NavViewModel) {
        navViewModel.navController.popBackStack()
    }

    @Composable
    override fun Content() {
        hiltViewModel<BookViewModel>().calibreBook.value?.ViewDetails()
    }

    suspend fun search(
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

    @SuppressLint("ComposableNaming")
    @Composable
    override fun floatingActionButton() {
        val openLibraryBookSearchViewModel: OpenLibraryBookSearchViewModel = hiltViewModel()
        val bookViewModel = hiltViewModel<BookViewModel>()
        val scope = rememberCoroutineScope()
        ExtendedFloatingActionButton(onClick = {
            scope.launch {
                search(
                    openLibraryBookSearchViewModel,
                    bookViewModel.calibreBook.value,
                    bookViewModel.openLibraryBook
                )
            }
        }, icon = {
            Icon(
                Icons.Filled.Search, contentDescription = "Favorite"
            )
        }, text = {
            Text("${bookViewModel.calibreBook.value?.title}")
            //Text(stringResource(R.string.get_book_info))
        })
    }
}