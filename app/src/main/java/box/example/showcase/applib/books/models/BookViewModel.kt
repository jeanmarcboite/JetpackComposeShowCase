package box.example.showcase.applib.books.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import box.example.showcase.applib.books.models.calibre.CalibreBook
import box.example.showcase.applib.books.models.openlibrary.OpenLibraryBook
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@Module
@InstallIn(ViewModelComponent::class)
object BookModule {
    val calibreBook: MutableState<CalibreBook?> = mutableStateOf(null)
    val openLibraryBook: MutableState<OpenLibraryBook?> = mutableStateOf(null)

    @Provides
    fun provideCalibreBook() = calibreBook

    @Provides
    fun provideOpenLibraryBook() = openLibraryBook
}

@HiltViewModel
class BookViewModel @Inject constructor(
    val calibreBook: MutableState<CalibreBook?>,
    val openLibraryBook: MutableState<OpenLibraryBook?>
) : ViewModel() {
    override fun toString(): String {
        return "BookViewModel($calibreBook, $openLibraryBook)"
    }
}
