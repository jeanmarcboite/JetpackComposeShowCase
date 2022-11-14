package box.example.showcase.ui.pages.books

import android.os.Bundle
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import box.example.showcase.R
import box.example.showcase.ui.Page
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Book

class BookDetailPage :
    Page(
        FontAwesomeIcons.Solid.Book,
        R.string.book_page_route,
        R.string.book_page_title,
        Icons.Default.ArrowBack,
        arguments = listOf(navArgument("bookID") { type = NavType.StringType })
    ) {
    var bookID = ""
    override fun showInDrawer() = false

    override fun parseArguments(arguments: Bundle?) = {
        Log.d("boxxx", "parse arguments: $arguments")
        bookID = arguments?.getString("bookID") ?: ""
    }

    override suspend fun onButtonClicked() {
        mainViewModel.navViewModel.navController.popBackStack()
    }

    @Composable
    override fun Content(openDrawer: () -> Unit) {
        Text("book: $bookID")
    }
}