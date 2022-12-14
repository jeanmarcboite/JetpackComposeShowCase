package box.example.showcase.ui.pages

import android.content.Context
import box.example.showcase.ui.Page
import box.example.showcase.ui.pages.about.AboutPage
import box.example.showcase.ui.pages.book.BookPage
import box.example.showcase.ui.pages.bored.BoredPage
import box.example.showcase.ui.pages.calibre.CalibreDatabasePage
import box.example.showcase.ui.pages.color.ColorPage
import box.example.showcase.ui.pages.home.HomePage
import box.example.showcase.ui.pages.lab.LabPage
import box.example.showcase.ui.pages.login.LoginPage
import box.example.showcase.ui.pages.notes.NotesPage
import box.example.showcase.ui.pages.openlibrary.OpenLibraryBookPage
import box.example.showcase.ui.pages.openlibrary.OpenLibrarySearchPage
import box.example.showcase.ui.pages.todo.TodoPage

fun mainPages(context: Context): HashMap<String, Page> {
    val pages = listOf(
        HomePage,
        ColorPage(),
        AboutPage(),
        CalibreDatabasePage,
        BookPage(),
        LabPage(),
        BoredPage(),
        OpenLibrarySearchPage(),
        OpenLibraryBookPage(),
        NotesPage(),
        TodoPage(),
        LoginPage(),
        Settings()
    )

    return pages.associateBy { context.resources.getString(it.route) } as HashMap<String, Page>
}