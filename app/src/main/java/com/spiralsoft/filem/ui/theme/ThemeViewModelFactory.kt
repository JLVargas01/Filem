package com.spiralsoft.filem.ui.theme

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ThemeViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val prefManager = ThemePreferenceManager(context.applicationContext)
        return ThemeViewModel(prefManager) as T
    }
}
