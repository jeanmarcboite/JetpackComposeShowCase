package box.example.showcase.ui.pages.calibre

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import box.example.showcase.R
import box.example.showcase.applib.books.components.openlibrary.View
import box.example.showcase.applib.books.models.BookViewModel
import box.example.showcase.ui.Tab
import compose.icons.TablerIcons
import compose.icons.tablericons.Cloud

object OpenLibraryBookTab : Tab(
    TablerIcons.Cloud,
    R.string.openlibrary_book_page_route,
    R.string.openlibrary_book_page_title,
) {
    @Composable
    override fun Content() {
        val bookViewModel = hiltViewModel<BookViewModel>()
        Log.d("boxxx [OpenLibrary:Content]", "book: ${bookViewModel.openLibraryBook.value}")
        bookViewModel.openLibraryBook.value?.View()
    }
}