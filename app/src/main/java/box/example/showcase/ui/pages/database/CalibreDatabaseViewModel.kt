package box.example.showcase.ui.pages.database

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import box.example.showcase.applib.books.models.calibre.CalibreBook
import box.example.showcase.applib.books.models.calibre.CalibreDatabase

class CalibreDatabaseViewModel : ViewModel() {
    var databaseVersion = mutableStateOf(0)
    val calibreDatabase: MutableState<CalibreDatabase?> = mutableStateOf(null)
    val book: MutableState<CalibreBook?> = mutableStateOf(null)
    fun getBook(calibreDatabase: CalibreDatabase?, uuid: String) {
        book.value = calibreDatabase?.uuidBookMap?.value?.get(uuid)
    }
}