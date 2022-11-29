package box.example.showcase.ui.pages.calibre

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import box.example.showcase.R
import box.example.showcase.applib.books.components.calibre.ViewDetails
import box.example.showcase.applib.books.models.calibre.CalibreBookViewModel
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
        val calibreBookViewModel: CalibreBookViewModel = hiltViewModel()
        Log.d("boxxx [DbBookPage:Content]", "book: ${calibreBookViewModel.book.value}")
        calibreBookViewModel.book.value?.ViewDetails()
    }
}