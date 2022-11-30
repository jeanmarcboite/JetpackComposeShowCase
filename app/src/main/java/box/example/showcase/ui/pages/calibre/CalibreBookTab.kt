package box.example.showcase.ui.pages.calibre

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import box.example.showcase.R
import box.example.showcase.applib.books.components.calibre.ViewDetails
import box.example.showcase.applib.books.models.BookViewModel
import box.example.showcase.ui.Tab
import compose.icons.TablerIcons
import compose.icons.tablericons.Database

object CalibreBookTab : Tab(
    TablerIcons.Database,
    R.string.calibre_book_page_route,
    R.string.calibre_book_page_title,
) {
    @Composable
    override fun Content() {
        //val bookViewModel = hiltViewModel<BookViewModel>()
        // Log.v("boxxx [CalibreBookTab:Content]", "book: ${bookViewModel.calibreBook.value}")
        hiltViewModel<BookViewModel>().calibreBook.value?.ViewDetails()
    }
}