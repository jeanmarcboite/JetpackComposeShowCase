package box.example.showcase.ui.pages.database

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import box.example.showcase.R
import box.example.showcase.applib.books.models.openlibrary.BookQueryType
import box.example.showcase.applib.books.models.openlibrary.OpenLibraryBookSearchViewModel
import box.example.showcase.ui.Page
import box.example.showcase.ui.pages.database.components.ViewDetails
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.BookReader
import kotlinx.coroutines.launch

class DbBookPage : Page(
    FontAwesomeIcons.Solid.BookReader,
    R.string.db_book_page_route,
    R.string.book_page_title,
    Icons.Default.ArrowBack,
    arguments = listOf(navArgument("bookID") { type = NavType.StringType })
) {
    var bookID = ""
    override fun showInDrawer() = false
    override fun parseArguments(arguments: Bundle?) {
        Log.v("boxxx", "parse arguments: $arguments")
        bookID = arguments?.getString("bookID") ?: ""
    }

    override suspend fun onButtonClicked() {
        mainViewModel.navViewModel.navController.popBackStack()
    }

    @Composable
    override fun Content() {
        val viewModel = mainViewModel.calibreDatabaseViewModel.calibreBookViewModel
        /*
        val book = remember {
            mutableStateOf<CalibreBook?>(null)
        }
        LaunchedEffect(true) {
            book.value = viewModel.calibreDatabase.value?.uuidBookMap?.value?.get(bookID)
        }

         */
        LaunchedEffect(true) {
            //book = viewModel.calibreDatabase.value?.uuidBookMap?.value?.get(bookID)
            viewModel.getBook(mainViewModel.calibreDatabaseViewModel.calibreDatabase.value, bookID)
        }
        viewModel.book.value?.ViewDetails()
    }

    @SuppressLint("ComposableNaming")
    @Composable
    override fun floatingActionButton() {
        val viewModel = mainViewModel.calibreDatabaseViewModel.calibreBookViewModel
        val openLibraryBookSearchViewModel: OpenLibraryBookSearchViewModel = hiltViewModel()
        val scope = rememberCoroutineScope()
        ExtendedFloatingActionButton(onClick = {
            if (viewModel.book.value != null) {
                scope.launch {
                    val isbn = viewModel.book.value!!.ISBN
                    if (isbn != null) {
                        openLibraryBookSearchViewModel.getBookByIsbn(
                            isbn
                        )
                    } else openLibraryBookSearchViewModel.getBooks(
                        viewModel.book.value!!.title!!, BookQueryType.Title
                    )
                }
            }
        }, icon = {
            Icon(
                Icons.Filled.Search, contentDescription = "Favorite"
            )
        }, text = {
            Text("${viewModel.book.value?.title}")
            //Text(stringResource(R.string.get_book_info))
        })
    }
}