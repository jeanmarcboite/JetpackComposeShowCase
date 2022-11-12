package box.example.showcase.applib.books

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class BookModule {
    @Provides
    fun bookService(): BookService {
        return OpenLibraryBookService()
    }
}