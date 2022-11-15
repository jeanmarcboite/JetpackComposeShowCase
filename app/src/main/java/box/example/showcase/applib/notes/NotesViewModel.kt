package box.example.showcase.applib.notes

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import box.example.showcase.applib.notes.models.Note
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: NotesRepository) : ViewModel() {
    val notes = mutableStateListOf<Note>()

    init {
        viewModelScope.launch {
            getAll()
        }
    }

    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note)
        getAll()
    }

    fun update(note: Note) = viewModelScope.launch {
        repository.update(note)
        getAll()
    }

    suspend fun getAll() {
        notes.clear()
        notes.addAll(repository.getAll())
    }
}

class NotesViewModelFactory(private val repository: NotesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}