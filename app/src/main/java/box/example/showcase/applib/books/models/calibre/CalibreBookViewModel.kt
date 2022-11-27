package box.example.showcase.applib.books.models.calibre

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@Module
@InstallIn(ViewModelComponent::class)
object CalibreBookViewModelHomeModule {
    val book: MutableState<CalibreBook?> = mutableStateOf(null)

    @Provides
    fun provideBook() = book
}

@HiltViewModel
class CalibreBookViewModel @Inject constructor(
    val book: MutableState<CalibreBook?>
) : ViewModel() {
    override fun toString(): String {
        return "CalibreBookViewModel(book=$book)"
    }
}
