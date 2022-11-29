package box.example.showcase.ui.pages.database

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import box.example.showcase.R
import box.example.showcase.applib.books.components.calibre.View
import box.example.showcase.applib.books.components.calibre.ViewSummary
import box.example.showcase.applib.books.models.calibre.CalibreAuthor
import box.example.showcase.applib.books.models.calibre.CalibreBook
import box.example.showcase.applib.books.models.calibre.CalibreBookViewModel
import box.example.showcase.applib.books.models.calibre.CalibreEntity
import box.example.showcase.ui.models.NavViewModel
import box.example.showcase.ui.theme.margin_half
import compose.icons.TablerIcons
import compose.icons.tablericons.Book
import compose.icons.tablericons.MoodHappy

abstract class DatabaseTableScreen(val list: List<CalibreEntity>?) {
    abstract val icon: ImageVector
    abstract val route: Int
    abstract val title: Int

    @Composable
    fun Content() {
        list?.apply {
            LazyColumn(
                contentPadding = PaddingValues(top = margin_half)
            ) {
                items(list) {
                    it.ItemView()
                }
            }
        }
    }

    @Composable
    abstract fun CalibreEntity.ItemView()
}

class BooksScreen(list: List<CalibreEntity>?) :
    DatabaseTableScreen(list) {
    override val icon = TablerIcons.Book
    override val route = R.string.calibre_book_table_route
    override val title = R.string.calibre_book_table_title

    @Composable
    override fun CalibreEntity.ItemView() {
        val calibreBookViewModel: CalibreBookViewModel = hiltViewModel()
        val navViewModel: NavViewModel = hiltViewModel()
        val path = "book/calibre"
        val book = this as CalibreBook
        Column {
            book.ViewSummary {
                val destination = "$path/${book.uuid}"
                calibreBookViewModel.book.value = book
                Log.d("boxxx", "navigate to $destination")
                navViewModel.navigate(destination)
            }
        }
    }
}

class AuthorsScreen(list: List<CalibreEntity>?) : DatabaseTableScreen(list) {
    override val icon = TablerIcons.MoodHappy
    override val route = R.string.calibre_author_table_route
    override val title = R.string.calibre_author_table_title

    @Composable
    override fun CalibreEntity.ItemView() {
        (this as CalibreAuthor).View()
    }
}