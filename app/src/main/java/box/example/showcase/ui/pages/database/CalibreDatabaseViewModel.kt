package box.example.showcase.ui.pages.database

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import box.example.showcase.applib.books.models.calibre.CalibreBook

class CalibreDatabaseViewModel : ViewModel() {
    val books = mutableStateOf<List<CalibreBook>?>(null)

}