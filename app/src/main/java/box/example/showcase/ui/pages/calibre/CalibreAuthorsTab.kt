package box.example.showcase.ui.pages.calibre

import androidx.compose.runtime.Composable
import box.example.showcase.R
import box.example.showcase.applib.books.components.calibre.View
import box.example.showcase.applib.books.models.calibre.CalibreAuthor
import box.example.showcase.applib.books.models.calibre.CalibreEntity
import compose.icons.TablerIcons
import compose.icons.tablericons.MoodHappy

object CalibreAuthorsTab : CalibreTab(
    TablerIcons.MoodHappy,
    R.string.calibre_authors_table_route,
    R.string.calibre_authors_table_title
) {
    @Composable
    override fun CalibreEntity.ItemView() {
        (this as CalibreAuthor).View()
    }
}