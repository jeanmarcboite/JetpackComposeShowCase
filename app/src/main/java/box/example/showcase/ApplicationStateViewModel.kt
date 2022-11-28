package box.example.showcase

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
object ApplicationStateModule {
    private val snackbarHostState = SnackbarHostState()

    @Provides
    fun snackbarHostState() = snackbarHostState
}

@HiltViewModel
class ApplicationStateViewModel @Inject constructor(
    val snackbarHostState: SnackbarHostState
) : ViewModel()