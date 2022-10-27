package box.example.showcase

import android.content.Context
import box.example.showcase.applib.SettingsPreferences
import box.example.showcase.applib.SettingsStore

val preferences = SettingsPreferences(
    booleans = mapOf(R.string.dark_mode to true),
)


class ApplicationSettings(context: Context) : SettingsStore(context, preferences) {
}