package box.example.showcase.ui.pages.database

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import box.example.showcase.applib.books.models.calibre.CalibreEntity

class CalibreDatabaseViewModel : ViewModel() {
    var databaseVersion = mutableStateOf(0)
    val books = mutableStateOf<List<CalibreEntity>?>(null)
    val authors = mutableStateOf<List<CalibreEntity>?>(null)
}