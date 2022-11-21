package box.example.showcase.ui.pages.database.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import box.example.showcase.applib.books.models.calibre.CustomColumn
import box.example.showcase.applib.books.models.calibre.CustomColumnEntry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Map.Entry<CustomColumnEntry, List<CustomColumn>>.View() {
    val entry = this
    Row() {
        Text((entry.key.name ?: "custom-column") + ":")
        entry.value.forEach {
            Badge { Text(it.toString()) }
        }
    }
}
