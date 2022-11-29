package box.example.showcase.ui.pages.calibre

import android.util.Log
import androidx.compose.runtime.Composable
import box.example.showcase.R
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
        Log.d("boxxx [OpenLibrary:Content]", "book: ")
    }
}