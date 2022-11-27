package box.example.showcase.applib.books.components.calibre

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import box.example.showcase.applib.books.models.calibre.CalibreAuthor
import box.example.showcase.ui.components.OutlinedCard


@Composable
fun CalibreAuthor.View() {
    OutlinedCard(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        label = { Text(name.toString()) }) {
        Column(modifier = Modifier.padding(8.dp)) {
            books.sortedBy {
                it.sort ?: it.title
            }.forEach {
                Text(it.title.toString())
            }
        }
    }
}