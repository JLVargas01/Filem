package com.spiralsoft.filem.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ThemeViewModel(private val prefManager: ThemePreferenceManager) : ViewModel() {
    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme = _isDarkTheme.asStateFlow()

    init {
        // Lee el valor guardado al iniciar
        viewModelScope.launch {
            prefManager.isDarkTheme.collect {
                _isDarkTheme.value = it
            }
        }
    }

    fun toggleTheme() {
        val newValue = !_isDarkTheme.value
        _isDarkTheme.value = newValue

        // Guarda el valor con DataStore
        viewModelScope.launch {
            prefManager.setDarkTheme(newValue)
        }
    }
}
