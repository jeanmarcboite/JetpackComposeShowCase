package box.example.showcase.applib.books.components.openlibrary

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
import box.example.showcase.applib.books.models.openlibrary.OpenLibraryApiHelper
import box.example.showcase.applib.books.models.openlibrary.OpenLibraryAuthor
import box.example.showcase.applib.books.models.openlibrary.OpenLibraryBook
import coil.compose.rememberAsyncImagePainter
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.SkullCrossbones


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenLibraryBook.View() {
    val book = this

    val scroll = rememberScrollState(0)

    val bookAuthors = remember {
        mutableStateOf<List<OpenLibraryAuthor?>>(listOf())
    }

    LaunchedEffect(key1 = true) {
        try {
            val api = OpenLibraryApiHelper.getInstance()
            bookAuthors.value = book.authors?.map {
                if (it.author == null)
                    null
                else {
                    Log.d("boxx [bookAuthors]", it.author.key)
                    val parts = it.author.key.split("/")
                    val value: OpenLibraryAuthor? = api.getAuthor(parts.last()).body()
                    Log.d("boxx [bookAuthors]", "${it.author.key} $value")
                    value
                }
            } ?: listOf()
            Log.v("boxx [bookAuthors]", "authors: ${bookAuthors.value}")
        } catch (e: Exception) {
            Log.e("boxx [bookAuthors]", e.message.toString())
        }
    }

    Column {
        Card(modifier = Modifier.padding(16.dp)) {
            Row {
                val cover = covers?.firstOrNull() //:= "240727"
                val coverUrl = "https://covers.openlibrary.org/b/id/$cover-L.jpg"
                val imageUrl = "https://covers.openlibrary.org/b/olid/OL7440033M-L.jpg"

                Image(
                    modifier = Modifier.padding(32.dp),
                    painter = rememberAsyncImagePainter(
                        model = coverUrl
                    ), contentDescription = imageUrl
                )
                Column {
                    val firstAuthor = bookAuthors.value.firstOrNull()?.name ?: ""
                    OutlinedTextField(
                        enabled = false,
                        value = title ?: "",
                        onValueChange = {},
                        label = {
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
                    bookAuthors.value.firstOrNull()?.personal_name?.apply {
                        Text(this, modifier = Modifier.padding(start = 32.dp))
                    }


                    book.description?.value?.apply {
                        Text(
                            this,
                            modifier = Modifier
                                .padding(start = 32.dp)
                                .height(92.dp)
                                .verticalScroll(scroll)
                        )
                    }
                }
            }
        }
        bookAuthors.value.forEach {
            it?.View(Modifier.padding(32.dp))
        }
    }
}

@Composable
fun OpenLibraryAuthor.View(modifier: Modifier = Modifier) {
    val scroll = rememberScrollState(0)
    Card(modifier) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                name ?: "",
                style = MaterialTheme.typography.titleLarge,
                fontStyle = FontStyle.Italic
            )
            Spacer(Modifier.weight(1f))

            Column {
                Text(birth_date ?: "")

                death_date?.apply {
                    Row {
                        Icon(
                            FontAwesomeIcons.Solid.SkullCrossbones,
                            contentDescription = "Death",
                            modifier = Modifier.size(24.dp)
                        )
                        Text(death_date)
                    }
                }
            }
        }
        alternate_names?.apply {
            Text(toString())
        }
        Text(
            bio ?: "", modifier = Modifier.verticalScroll(scroll)
        )
        Text(wikipedia ?: "")
    }
}
