package box.example.showcase.ui.pages.books

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import box.example.showcase.R
import box.example.showcase.applib.books.BookList
import box.example.showcase.applib.books.BookQueryType
import box.example.showcase.applib.books.BookSearchViewModel
import box.example.showcase.ui.Page
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.ArrowLeft
import compose.icons.fontawesomeicons.solid.BookReader
import kotlinx.coroutines.launch

class BooksPage :
    Page(
        FontAwesomeIcons.Solid.BookReader,
        R.string.books_page_route,
        R.string.books_page_title
    ) {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
    @Composable
    override fun Content(openDrawer: () -> Unit) {
        var progressVisible by rememberSaveable {
            mutableStateOf(false)
        }
        var label by rememberSaveable {
            mutableStateOf("search titles")
        }
        val bookList: MutableState<BookList?> = remember {
            mutableStateOf(null)
        }
        val scope = rememberCoroutineScope()
        var searchString by rememberSaveable {
            mutableStateOf("lord of the")
        }
        val bookSearchViewModel: BookSearchViewModel = hiltViewModel()
        val keyboardController = LocalSoftwareKeyboardController.current

        val queryOptions = mapOf(
            "Any" to BookQueryType.Any,
            "Title" to BookQueryType.Title, "Author" to BookQueryType.Author
        )
        val (selectedOption, onOptionSelected) = remember { mutableStateOf("Any") }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                value = searchString,
                singleLine = true,
                label = { Text(label) },
                onValueChange = {
                    searchString = it
                },
                trailingIcon = {
                    Icon(
                        FontAwesomeIcons.Solid.ArrowLeft,
                        "error",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(16.dp)
                    )
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        progressVisible = true
                        scope.launch {
                            keyboardController?.hide()
                            bookList.value = bookSearchViewModel.getBooks(
                                searchString,
                                queryOptions[selectedOption]!!
                            ).getOrNull()
                            Log.v("boxxx", "got: ${bookList.value}")
                            progressVisible = false
                        }
                    }
                ))
            RadioButton(queryOptions.keys.toList(), selectedOption, onOptionSelected)
            if (progressVisible) {
                CircularProgressIndicator(
                    modifier = Modifier.size(100.dp),
                    color = Color.Green,
                    strokeWidth = 10.dp
                )
            }
            bookList.value?.let { bookList ->
                label = "${bookList.numFound} books found"
                LazyColumn {
                    items(bookList.docs) {
                        it.ViewSummary()
                    }
                }
                Text(bookList.toString())
            }
        }
    }

    @Composable
    fun RadioButton(
        radioOptions: List<String>,
        selectedOption: String,
        onOptionSelected: (String) -> Unit
    ) {
        Row() {
            radioOptions.forEach { text ->
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = {
                                onOptionSelected(text)
                            }
                        )
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) }
                    )
                    Text(
                        text = text,
                        modifier = Modifier.padding(start = 2.dp)
                    )
                }
            }
        }
    }

}