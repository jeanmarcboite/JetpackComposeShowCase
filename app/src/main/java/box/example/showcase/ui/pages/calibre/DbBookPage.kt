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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import box.example.showcase.R
import box.example.showcase.applib.books.components.calibre.ViewDetails
import box.example.showcase.applib.books.models.calibre.CalibreBook
import box.example.showcase.applib.books.models.calibre.CalibreBookViewModel
import box.example.showcase.applib.books.models.openlibrary.BookQueryType
import box.example.showcase.applib.books.models.openlibrary.OpenLibraryBook
import box.example.showcase.applib.books.models.openlibrary.OpenLibraryBookList
import box.example.showcase.applib.books.models.openlibrary.OpenLibraryBookSearchViewModel
import box.example.showcase.ui.Page
import box.example.showcase.ui.models.NavViewModel
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.BookReader
import kotlinx.coroutines.launch

class DbBookPage : Page(
    FontAwesomeIcons.Solid.BookReader,
    R.string.db_book_page_route,
    R.string.book_page_title,
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
        val calibreBookViewModel: CalibreBookViewModel = hiltViewModel()
        Log.d("boxxx [DbBookPage:Content]", "book: ${calibreBookViewModel.book.value}")
        calibreBookViewModel.book.value?.ViewDetails()
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
            if (result == null || result.isFailure) {
                // OpenLibraryBookList(docs=[], numFound=0, numFoundExact=true, num_found=0, offset=null, q=, start=0)
                val resultBookList: Result<OpenLibraryBookList?> =
                    openLibraryBookSearchViewModel.getBooks(
                        book.title!!, BookQueryType.Title
                    )
            } else {
                val openLibraryBook: OpenLibraryBook? = result.getOrNull()
            }
        }
    }

    @SuppressLint("ComposableNaming")
    @Composable
    override fun floatingActionButton() {
        val calibreBookViewModel: CalibreBookViewModel = hiltViewModel()
        val openLibraryBookSearchViewModel: OpenLibraryBookSearchViewModel = hiltViewModel()
        val book = calibreBookViewModel.book.value
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
            //Text(stringResource(R.string.get_book_info))
        })
    }
}