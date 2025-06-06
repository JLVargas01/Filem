package com.spiralsoft.filem.data.source

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

// Crear DataStore
private val Context.dataStore by preferencesDataStore(name = "settings")

class ThemePreferenceManager(private val context: Context) {
    companion object {
        val DARK_THEME_KEY = booleanPreferencesKey("dark_theme_enabled")
    }

    // Leer valor (Flow<Boolean>)
    val isDarkTheme: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[DARK_THEME_KEY] ?: false // valor por defecto: claro
        }

    // Guardar valor
    suspend fun setDarkTheme(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_THEME_KEY] = enabled
        }
    }
}
