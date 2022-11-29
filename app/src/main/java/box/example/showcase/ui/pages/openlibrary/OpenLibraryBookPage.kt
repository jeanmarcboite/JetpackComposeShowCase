package box.example.showcase.ui.pages.openlibrary

import android.os.Bundle
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import box.example.showcase.R
import box.example.showcase.applib.books.components.openlibrary.View
import box.example.showcase.applib.books.models.openlibrary.OpenLibraryBook
import box.example.showcase.applib.books.models.openlibrary.OpenLibraryBookSearchViewModel
import box.example.showcase.ui.Page
import box.example.showcase.ui.models.NavViewModel
import compose.icons.TablerIcons
import compose.icons.tablericons.Book

class OpenLibraryBookPage() :
    Page(
        TablerIcons.Book,
        R.string.openlibrary_book_page_route,
        R.string.openlibrary_book_page_title,
        Icons.Default.ArrowBack,
    ) {
    var bookID = ""
    override fun showInDrawer() = false

    override fun parseArguments(arguments: Bundle?) {
        Log.v("boxxx", "parse arguments: $arguments")
        //val arguments = listOf(navArgument("bookID") { type = NavType.StringType })
        bookID = arguments?.getString("bookID") ?: ""
    }

    override suspend fun onButtonClicked(navViewModel: NavViewModel) {
        navViewModel.navController.popBackStack()
    }

    @Composable
    override fun Content() {
        val openLibraryBookSearchViewModel: OpenLibraryBookSearchViewModel = hiltViewModel()
        val openLibraryBook = remember {
            mutableStateOf<OpenLibraryBook?>(null)
        }
        LaunchedEffect(true) {
            openLibraryBook.value = openLibraryBookSearchViewModel.getBook(bookID).getOrNull()
        }
        openLibraryBook.value?.View()

    }
}