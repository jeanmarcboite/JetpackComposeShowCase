package box.example.showcase.ui.pages.calibre

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import box.example.showcase.R
import box.example.showcase.applib.books.components.calibre.ViewSummary
import box.example.showcase.applib.books.models.calibre.CalibreBook
import box.example.showcase.applib.books.models.calibre.CalibreBookViewModel
import box.example.showcase.applib.books.models.calibre.CalibreEntity
import box.example.showcase.ui.models.NavViewModel
import compose.icons.TablerIcons
import compose.icons.tablericons.Book

object CalibreBooksTab : CalibreTab(
    TablerIcons.Book,
    R.string.calibre_books_table_route,
    R.string.calibre_books_table_title
) {
    @Composable
    override fun CalibreEntity.ItemView() {
        val calibreBookViewModel: CalibreBookViewModel = hiltViewModel()
        val navViewModel: NavViewModel = hiltViewModel()
        val path = "book/calibre"
        val route = LocalContext.current.getString(R.string.book_page_route)
        val book = this as CalibreBook
        Column {
            book.ViewSummary {
                val destination = "$path/${book.uuid}"
                calibreBookViewModel.book.value = book
                navViewModel.navigate(route)
            }
        }
    }
}