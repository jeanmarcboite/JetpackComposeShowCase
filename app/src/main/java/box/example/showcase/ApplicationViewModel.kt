package box.example.showcase

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.ViewModel
import box.example.showcase.ApplicationModule.DARK_MODE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {
    lateinit var applicationSettings: ApplicationSettings
    lateinit var DARK_MODE: String
    fun readApplicationSettings(context: Context) {
        applicationSettings = ApplicationSettings(context)

        DARK_MODE = context.resources.getString(R.string.dark_mode)
        applicationSettings.darkMode.value =
            applicationSettings[booleanPreferencesKey(DARK_MODE)] ?: false
    }

    @Provides
    fun appSettings() = applicationSettings
}

@HiltViewModel
class ApplicationViewModel @Inject constructor(
    val applicationSettings: ApplicationSettings
) : ViewModel() {
    fun setDarkMode(value: Boolean) {
        applicationSettings.darkMode.value = value
        applicationSettings[booleanPreferencesKey(DARK_MODE)] = value
    }
}