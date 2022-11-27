package box.example.showcase.applib.bored

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import java.util.*
import javax.inject.Inject

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelMovieModule {
    @Provides
    @ViewModelScoped
    fun provideRepo(handle: SavedStateHandle) =
        handle.get<String>("movie-id")?.let { MovieRepository(it) };

    class MovieRepository(val string: String, val uuid: UUID = UUID.randomUUID())
}

class MovieDetailFetcher @Inject constructor(
    val movieRepo: ViewModelMovieModule.MovieRepository?
) {
    override fun toString(): String {
        return movieRepo.toString()
    }
}

class MoviePosterFetcher @Inject constructor(
    val movieRepo: ViewModelMovieModule.MovieRepository?
) {
    override fun toString(): String {
        return movieRepo.toString()
    }
}

@HiltViewModel
class MovieViewModel @Inject constructor(
    val detailFetcher: MovieDetailFetcher,
    val posterFetcher: MoviePosterFetcher
) : ViewModel() {
    init {
        // Both detailFetcher and posterFetcher will contain the same instance of
        // the MovieRepository.
    }
}