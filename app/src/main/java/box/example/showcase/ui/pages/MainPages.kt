package box.example.showcase

import android.content.Context
import box.example.showcase.ui.Page
import box.example.showcase.ui.pages.about.AboutPage
import box.example.showcase.ui.pages.bored.BoredPage
import box.example.showcase.ui.pages.color.ColorPage
import box.example.showcase.ui.pages.home.HomePage
import box.example.showcase.ui.pages.login.LoginPage
import box.example.showcase.ui.pages.notes.NotesPage

fun mainPages(context: Context, mainViewModel: MainViewModel): Map<String, Page> {
    val pages = listOf(
        HomePage(),
        ColorPage(),
        AboutPage(),
        BoredPage(),
        NotesPage(),
        LoginPage()
    )
    pages.forEach {
        it.mainViewModel = mainViewModel
    }

    return pages.associateBy { context.resources.getString(it.route) }
}