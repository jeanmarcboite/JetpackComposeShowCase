package box.example.showcase.ui.models

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class Repository @Inject constructor()

@HiltViewModel
class ExampleViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel()