package box.example.showcase.applib

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import box.example.showcase.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class SettingsPreferences(
    val strings: Map<Int, Int> = mapOf(),
    val booleans: Map<Int, Boolean> = mapOf(),
    val dropdown: Map<Int, Int> = mapOf()
)

open class SettingsStore(
    val context: Context, val preferences: SettingsPreferences
) {
    val dataStore = context.dataStore

    fun clear() {
        runBlocking {
            dataStore.edit {
                it.clear()
            }
        }
        getDefaults()
    }

    private suspend fun <T> edit(key: Preferences.Key<T>, value: T) {
        dataStore.edit {
            it[key] = value
        }
    }

    fun <T> getFlow(key: Preferences.Key<T>): Flow<T?> = context.dataStore.data.map { preferences ->
        preferences[key]
    }

    operator fun <T> get(key: Preferences.Key<T>): T? {
        var t: T?
        runBlocking {
            t = getFlow(key).first()
        }

        return t
    }

    operator fun <T> set(key: Preferences.Key<T>, value: T) {
        runBlocking {
            edit(key, value)
        }
    }

    fun getDefaults() {
        preferences.strings.forEach { (key, value) ->
            if (get(stringPreferencesKey(context.resources.getString(key))) == null)
                this[stringPreferencesKey(context.resources.getString(key))] =
                    context.resources.getString(value)
        }
        preferences.booleans.forEach { (key, value) ->
            if (get(booleanPreferencesKey(context.resources.getString(key))) == null)
                this[booleanPreferencesKey(context.resources.getString(key))] = value
        }
        preferences.dropdown.forEach { (key, value) ->
            if (get(stringPreferencesKey(context.resources.getString(key))) == null)

                this[stringPreferencesKey(context.resources.getString(key))] =
                    context.resources.getString(value)
        }
    }

    init {
        if (!initialized) {
            getDefaults()
            initialized = true
        }
    }

    companion object {
        var initialized = false
    }

}