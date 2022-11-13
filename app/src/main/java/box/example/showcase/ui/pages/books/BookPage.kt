package box.example.showcase.ui.pages.books

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import box.example.showcase.applib.books.BookSearchViewModel
import box.example.showcase.ui.Page
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.BookReader
import kotlinx.coroutines.launch

class BooksPage() :
    Page(
        FontAwesomeIcons.Solid.BookReader,
        R.string.books_page_route,
        R.string.books_page_title
    ) {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
    @Composable
    override fun Content(openDrawer: () -> Unit) {
        var progressVisible by rememberSaveable() {
            mutableStateOf(false)
        }
        val bookList: MutableState<BookList?> = remember {
            mutableStateOf(null)
        }
        val scope = rememberCoroutineScope()
        var searchString by rememberSaveable {
            mutableStateOf("")
        }
        val bookSearchViewModel: BookSearchViewModel = hiltViewModel()
        val keyboardController = LocalSoftwareKeyboardController.current
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
                label = { Text("Search string") },
                onValueChange = {
                    searchString = it
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        progressVisible = true
                        scope.launch {
                            keyboardController?.hide()
                            bookList.value = getBooks(bookSearchViewModel, searchString).getOrNull()
                            Log.i("boxxx", "got: ${bookList.value}")
                            progressVisible = false
                        }
                    }
                ))
            if (progressVisible) {
                CircularProgressIndicator(
                    modifier = Modifier.size(100.dp),
                    color = Color.Green,
                    strokeWidth = 10.dp
                )
            }
            bookList.value?.let { Text(it.toString()) }
        }
    }


    private suspend fun getBooks(
        bookSearchViewModel: BookSearchViewModel,
        query: String
    ): Result<BookList?> {
        //progressBar.visibility = View.VISIBLE
        val result = bookSearchViewModel.getBooks(query)
        //progressBar.visibility = View.GONE
/*
if (error != null)
{

Alerment(error.title, error.message).show(
    childFragmentManager,
    AlertDialogFragment.TAG
)
} else
{
recyclerView.adapter?.notifyDataSetChanged()
}
}
*/
        return result
    }
}