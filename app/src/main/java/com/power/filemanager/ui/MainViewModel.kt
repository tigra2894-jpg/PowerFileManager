package com.power.filemanager.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.power.filemanager.data.StorageRepository
import com.power.filemanager.model.FileItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: StorageRepository = StorageRepository()
) : ViewModel() {

    private val _files = MutableStateFlow<List<FileItem>>(emptyList())
    val files: StateFlow<List<FileItem>> = _files.asStateFlow()

    private val _currentPath = MutableStateFlow("/storage/emulated/0")
    val currentPath: StateFlow<String> = _currentPath.asStateFlow()

    init {
        loadDirectory(_currentPath.value)
    }

    fun loadDirectory(path: String) {
        viewModelScope.launch {
            _currentPath.value = path
            repository.getFilesInDirectory(path).onSuccess { fileList ->
                _files.value = fileList
            }
        }
    }
}
