package box.example.showcase.ui.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.ViewModel
import box.example.showcase.applib.books.models.calibre.CalibreDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named

class DocumentTree {
    var value: DocumentFile? = null
}

@Module
@InstallIn(ActivityRetainedComponent::class)
object CalibreDatabaseModule {
    val databaseVersion = mutableStateOf(0)
    val calibreDatabase: MutableState<CalibreDatabase?> = mutableStateOf(null)
    val documentTree = DocumentTree()

    @Provides
    @Named("version")
    fun version() = databaseVersion

    @Provides
    fun database() = calibreDatabase

    @Provides
    fun documentTree() = documentTree
}

@HiltViewModel
class CalibreDatabaseViewModel @Inject constructor(
    @Named("version") val version: MutableState<Int>,
    val database: MutableState<CalibreDatabase?>,
    val documentTree: DocumentTree
) : ViewModel()