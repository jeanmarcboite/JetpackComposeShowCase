package box.example.showcase.applib.books.models.openlibrary

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class OpenLibraryBookModule {
    @Provides
    fun bookService(): BookService {
        return OpenLibraryBookService()
    }
}