package box.example.showcase.ui.pages.books

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import box.example.showcase.applib.books.OpenLibraryApiHelper
import box.example.showcase.applib.books.models.Author
import box.example.showcase.applib.books.models.Book


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Book.View() {
    val book = this
    val bookAuthors = remember {
        mutableStateOf<List<Author?>>(listOf())
    }

    LaunchedEffect(key1 = true) {
        val api = OpenLibraryApiHelper.getInstance()
        bookAuthors.value = book.authors.map {
            Log.d("boxx [bookAuthors]", it.author.key.toString())
            val parts = it.author.key.split("/")
            val value: Author? = api.getAuthor(parts.last()).body()
            Log.d("boxx [bookAuthors]", "${it.author.key} $value")
            value
        }
        Log.d("boxx [bookAuthors]", bookAuthors.value.toString())
    }

    Column {
        Card() {
            val firstAuthor = bookAuthors.value.firstOrNull()?.name ?: ""
            OutlinedTextField(enabled = false, value = title,
                onValueChange = {}, label = {
                    Text(
                        firstAuthor,
                        style = MaterialTheme.typography.labelMedium,
                        fontStyle = FontStyle.Italic
                    )
                },
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            )
        }
        bookAuthors.value.forEach {
            it?.View()
        }
    }
}

@Composable
fun Author.View() {
    val scroll = rememberScrollState(0)
    Card {
        Row {
            Text(name)
            Text(birth_date)
            Text(death_date)
        }
        Text(alternate_names.toString())
        Text(
            bio, modifier = Modifier.verticalScroll(scroll)
        )
        Text(wikipedia)
    }
}
