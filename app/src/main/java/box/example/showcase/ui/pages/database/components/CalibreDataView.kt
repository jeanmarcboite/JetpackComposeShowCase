package box.example.showcase.ui.pages.database.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import box.example.showcase.applib.books.models.calibre.CalibreData
import box.example.showcase.ui.components.OutlinedCard

@Composable
fun CalibreData.View() {
    Row() {
        OutlinedCard(label = { Text("fmt") }) {
            if (format != null) {
                Text(
                    format, textAlign = TextAlign.Center
                )
            }
        }
        OutlinedCard(label = { Text("size") }) {
            Text(uncompressed_size.toString())
        }
        OutlinedCard(label = { Text("name") }) {
            if (name != null) {
                Text(name)
            }
        }
    }
}
