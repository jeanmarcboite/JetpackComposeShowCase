package box.example.showcase.ui.pages.database.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import box.example.showcase.applib.books.models.calibre.CalibreAuthor
import box.example.showcase.ui.components.OutlinedCard


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalibreAuthor.View() {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {
        OutlinedCard(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            label = { Text(name.toString()) }) {
            Column(modifier = Modifier.padding(8.dp)) {
                books.sortedBy {
                    it.title
                }.forEach {
                    Text(it.title.toString())
                }
            }
        }
    }
}