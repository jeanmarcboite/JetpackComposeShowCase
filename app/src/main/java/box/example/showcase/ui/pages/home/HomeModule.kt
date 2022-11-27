package box.example.showcase.ui.pages.home

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import java.util.*

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelHomeModule {
    val uuid: UUID = UUID.randomUUID()

    @Provides
    @ViewModelScoped
    fun provideUUID() = uuid
}