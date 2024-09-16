package com.radlance.presentation

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

import javax.inject.Inject

class UserPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    val currentLocation: Flow<String> = dataStore.data.catch {
        if (it is IOException) {
            Log.e(TAG, "Error reading preferences.", it)
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map { preferences ->
        preferences[CURRENT_LOCATION] ?: "United States:Mountain View"
    }

    suspend fun saveLocationPreferences(currentLocation: String) {
        dataStore.edit { preferences ->
            preferences[CURRENT_LOCATION] = currentLocation
        }
    }

    private companion object {
        val CURRENT_LOCATION = stringPreferencesKey("user_location")
        const val TAG = "UserPreferencesRepo"
    }
}