package box.example.showcase.ui.pages.calibre

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector
import box.example.showcase.applib.books.models.calibre.CalibreEntity
import box.example.showcase.ui.Tab
import box.example.showcase.ui.theme.margin_half

abstract class CalibreTab(
    icon: ImageVector,
    route: Int,
    title: Int
) : Tab(icon, route, title) {
    val list: MutableState<List<CalibreEntity>> = mutableStateOf(listOf())

    @Composable
    override fun Content() {
        list.value.apply {
            LazyColumn(
                contentPadding = PaddingValues(top = margin_half)
            ) {
                items(list.value) {
                    it.ItemView()
                }
            }
        }
    }

    @Composable
    abstract fun CalibreEntity.ItemView()
}