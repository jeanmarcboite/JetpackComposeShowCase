package box.example.showcase.ui.pages.database

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import box.example.showcase.applib.books.models.calibre.CalibreBookViewModel
import box.example.showcase.applib.books.models.calibre.CalibreDatabase

class CalibreDatabaseViewModel : ViewModel() {
    var databaseVersion = mutableStateOf(0)
    val calibreDatabase: MutableState<CalibreDatabase?> = mutableStateOf(null)
    lateinit var calibreBookViewModel: CalibreBookViewModel
}