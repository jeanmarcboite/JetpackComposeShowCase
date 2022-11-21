package box.example.showcase.ui.pages.database.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import box.example.showcase.applib.books.models.calibre.CustomColumn
import box.example.showcase.applib.books.models.calibre.CustomColumnEntry

@Composable
fun Map.Entry<CustomColumnEntry, List<CustomColumn>>.View() {
    val entry = this
    Row() {
        Text((entry.key.name ?: "custom-column") + ":")
        entry.value.forEach {
            Text(it.toString())
        }
    }

}
