package box.example.showcase.applib.notes

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import box.example.showcase.applib.notes.models.Note
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.launch
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
object NotesModule {
    val repository = mutableStateOf<NotesRepository?>(null)
    private val notes: SnapshotStateList<Note> = mutableStateListOf()

    @Provides
    fun repository() = repository

    @Provides
    fun notes() = notes
}

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val repository: MutableState<NotesRepository>,
    val notes: SnapshotStateList<Note>
) : ViewModel() {
    init {
        // TODO
        viewModelScope.launch {
            getAll()
        }
    }

    fun insert(note: Note) = viewModelScope.launch {
        repository.value.insert(note)
        getAll()
    }

    fun update(note: Note) = viewModelScope.launch {
        repository.value.update(note)
        getAll()
    }

    suspend fun getAll() {
        notes.clear()
        notes.addAll(repository.value.getAll())
    }
}
/*
class NotesViewModelFactory(private val repository: NotesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
*/