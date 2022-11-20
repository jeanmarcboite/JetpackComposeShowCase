package box.example.showcase.ui.pages.database.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import box.example.showcase.applib.books.models.calibre.CalibreBook

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalibreBook.View() {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {
        val book_authors = authors.joinToString {
            it.name.toString()
        }
        OutlinedTextField(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            enabled = false,
            label = { Text(book_authors) },
            value = title.toString(),
            onValueChange = {})
    }
}