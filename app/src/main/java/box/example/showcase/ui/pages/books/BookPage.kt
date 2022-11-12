package box.example.showcase.ui.pages.books

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import box.example.showcase.R
import box.example.showcase.applib.books.BookViewModel
import box.example.showcase.ui.Page
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.BookReader

class BooksPage() :
    Page(
        FontAwesomeIcons.Solid.BookReader,
        R.string.books_page_route,
        R.string.books_page_title
    ) {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(openDrawer: () -> Unit) {
        var searchString by rememberSaveable {
            mutableStateOf("")
        }
        val bookViewModel: BookViewModel = hiltViewModel()

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
                })
        }
    }
}
