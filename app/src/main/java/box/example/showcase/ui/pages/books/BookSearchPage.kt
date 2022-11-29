package box.example.showcase.ui.pages.books

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
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
import box.example.showcase.applib.books.components.openlibrary.View
import box.example.showcase.applib.books.models.openlibrary.BookQueryType
import box.example.showcase.applib.books.models.openlibrary.OpenLibraryBookSearchViewModel
import box.example.showcase.ui.Page
import box.example.showcase.ui.models.NavViewModel
import compose.icons.TablerIcons
import compose.icons.tablericons.Book
import compose.icons.tablericons.CaretLeft
import kotlinx.coroutines.launch

class BookSearchPage :
    Page(
        TablerIcons.Book,
        R.string.books_page_route,
        R.string.books_page_title
    ) {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
    @Composable
    override fun Content() {
        val navViewModel = hiltViewModel<NavViewModel>()
        var progressVisible by rememberSaveable {
            mutableStateOf(false)
        }
        var label by rememberSaveable {
            mutableStateOf("search titles")
        }
        val scope = rememberCoroutineScope()
        var searchString by rememberSaveable {
            mutableStateOf("lord of the rings")
        }
        val openLibraryBookSearchViewModel: OpenLibraryBookSearchViewModel = hiltViewModel()
        val keyboardController = LocalSoftwareKeyboardController.current

        val queryOptions = mapOf(
            "Any" to BookQueryType.Any,
            "Title" to BookQueryType.Title, "OpenLibraryAuthor" to BookQueryType.Author
        )
        val (selectedOption, onOptionSelected) = remember { mutableStateOf("Any") }
        fun search() {
            progressVisible = true
            scope.launch {
                keyboardController?.hide()
                openLibraryBookSearchViewModel.getBooks(
                    searchString,
                    queryOptions[selectedOption]!!
                )
                progressVisible = false
            }
        }

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
                        TablerIcons.CaretLeft,
                        "error",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier
                            .size(16.dp)
                            .clickable() {
                                searchString = ""
                            }
                    )
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        search()
                    }
                ))
            Row {
                RadioButton(queryOptions.keys.toList(), selectedOption, onOptionSelected)
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        search()
                    },
                    modifier = Modifier
                        .padding(1.dp)
                        .width(128.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text("Search")
                }
            }
            if (progressVisible) {
                CircularProgressIndicator(
                    modifier = Modifier.size(100.dp),
                    color = Color.Green,
                    strokeWidth = 10.dp
                )
            }
            openLibraryBookSearchViewModel.openLibraryBookList.value?.let { bookList ->
                label = "${bookList.numFound} books found"
                LazyColumn {
                    items(bookList.docs) {
                        it.View() {
                            val destination = "book${it.key}"
                            Log.d("boxxx", "navigate to $destination")
                            navViewModel.navigate(destination)
                        }
                    }
                }
                //Text(openLibraryBookList.toString())
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