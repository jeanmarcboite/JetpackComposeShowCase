package box.example.showcase.applib.bored

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlin.properties.Delegates

@Module
@InstallIn(SingletonComponent::class)
class BoredModule {
    var id by Delegates.notNull<Int>()

    init {
        id = object_id++
    }

    @Provides
    fun boredID(): Int {
        return id
    }

    companion object stamp {
        var object_id = 42
    }
}
