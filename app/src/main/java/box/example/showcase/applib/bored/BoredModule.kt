package box.example.showcase.applib.bored

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
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

    @Provides
    @Named("other")
    fun otherID(): Int {
        return id + 1
    }


    @Provides
    fun boredIDD(): Double {
        return 3.14
    }

    companion object stamp {
        var object_id = 42
    }
}

