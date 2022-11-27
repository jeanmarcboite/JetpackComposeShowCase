package box.example.showcase.applib.books.components.openlibrary

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import box.example.showcase.applib.books.models.openlibrary.OpenLibraryDoc

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenLibraryDoc.View(onClick: () -> Unit) {
    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            onClick = onClick
        ) {
            OutlinedTextField(
                readOnly = true,
                enabled = false,
                value = title,
                onValueChange = {},
                label = {
                    Text(
                        text = author_name?.get(0) ?: "",
                        style = MaterialTheme.typography.labelMedium,
                        fontStyle = FontStyle.Italic
                    )
                },
                maxLines = 2,
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            )
            subtitle?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelMedium,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}
