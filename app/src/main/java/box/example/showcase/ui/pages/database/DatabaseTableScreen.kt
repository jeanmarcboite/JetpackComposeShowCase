package box.example.showcase.ui.pages.database

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import box.example.showcase.R
import box.example.showcase.applib.books.models.calibre.CalibreAuthor
import box.example.showcase.applib.books.models.calibre.CalibreBook
import box.example.showcase.applib.books.models.calibre.CalibreEntity
import box.example.showcase.ui.pages.database.components.View
import box.example.showcase.ui.theme.margin_half
import compose.icons.TablerIcons
import compose.icons.tablericons.Book
import compose.icons.tablericons.MoodHappy

abstract class DatabaseTableScreen(val list: List<CalibreEntity>?) {
    abstract val icon: ImageVector
    abstract val route: Int
    abstract val title: Int

    @Composable
    fun Content(Content: @Composable (CalibreEntity) -> Unit) {
        list?.apply {
            LazyColumn(
                contentPadding = PaddingValues(top = margin_half)
            ) {
                items(list) {
                    Content(it)
                }
            }
        }
    }

    @Composable
    abstract fun Content()
}

class BooksScreen(list: List<CalibreEntity>?) : DatabaseTableScreen(list) {
    override val icon = TablerIcons.Book
    override val route = R.string.calibre_book_table_route
    override val title = R.string.calibre_book_table_title

    @Composable
    override fun Content() {
        Content {
            (it as CalibreBook).View()
        }
    }
}

class AuthorsScreen(list: List<CalibreEntity>?) : DatabaseTableScreen(list) {
    override val icon = TablerIcons.MoodHappy
    override val route = R.string.calibre_author_table_route
    override val title = R.string.calibre_author_table_title

    @Composable
    override fun Content() {
        Content {
            (it as CalibreAuthor).View()
        }
    }
}