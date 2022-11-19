package box.example.showcase.ui.pages.database

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.vector.ImageVector
import box.example.showcase.R
import box.example.showcase.applib.books.models.calibre.MetadataBook
import box.example.showcase.ui.pages.database.components.View
import box.example.showcase.ui.theme.margin_half
import compose.icons.TablerIcons
import compose.icons.tablericons.Book

interface DatabaseTableScreen {
    val icon: ImageVector
    val route: Int
    val title: Int

    @Composable
    fun content()
}

class BooksScreen(val list: MutableState<List<MetadataBook>?>) : DatabaseTableScreen {
    override val icon = TablerIcons.Book
    override val route = R.string.calibre_book_table_route
    override val title = R.string.calibre_book_table_title

    @Composable
    override fun content() {
        if (list.value != null) {
            Log.i("boxxxx [books]", list.value.toString())
        }

        list.value?.apply {
            LazyColumn(
                contentPadding = PaddingValues(top = margin_half)
            ) {
                items(list.value!!) {
                    it.View()
                }
            }
        }
    }
}