package com.spiralsoft.filem.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.File
import android.os.Environment
import kotlinx.coroutines.flow.StateFlow

class FileExplorerViewModel : ViewModel() {

    private val _currentPath = MutableStateFlow(Environment.getExternalStorageDirectory().absolutePath)
    val currentPath: StateFlow<String> = _currentPath

    private val _files = MutableStateFlow<List<File>>(emptyList())
    val files: StateFlow<List<File>> = _files

    init {
        loadFiles(_currentPath.value)
    }

    fun loadFiles(path: String) {
        _currentPath.value = path
        _files.value = getFiles(path)
    }

    private fun getFiles(path: String): List<File> {
        val dir = File(path)
        return dir.listFiles()
            ?.filter { it.exists() && it.canRead() }
            ?.sortedWith(compareBy<File> { !it.isDirectory }.thenBy { it.name.lowercase() })
            ?: emptyList()
    }

    fun goBack() {
        File(_currentPath.value).parent?.let {
            loadFiles(it)
        }
    }
}
