package box.example.showcase.ui.pages.database

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import box.example.showcase.applib.books.models.calibre.CalibreBook

class CalibreBookViewModel {
    val book: MutableState<CalibreBook?> = mutableStateOf(null)
}