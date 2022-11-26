package box.example.showcase.applib.books.models.calibre

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class CalibreBookService {
    fun getBook(calibreDatabase: CalibreDatabase?, uuid: String): CalibreBook? {
        return calibreDatabase?.uuidBookMap?.value?.get(uuid)
    }
}

@Module
@InstallIn(SingletonComponent::class)
class CalibreDatabaseModule {
    @Provides
    fun calibreBookService(): CalibreBookService {
        return CalibreBookService()
    }
}

@HiltViewModel
class CalibreBookViewModel @Inject constructor(
    private val calibreBookService: CalibreBookService
) : ViewModel() {
    val book: MutableState<CalibreBook?> = mutableStateOf(null)

    fun getBook(calibreDatabase: CalibreDatabase?, uuid: String) {
        book.value = calibreBookService.getBook(calibreDatabase, uuid)
    }
}
