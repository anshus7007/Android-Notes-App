package com.anshu.dynamicnotes.folder_view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anshu.dynamicnotes.repository.FoldersRepository

@Suppress("UNCHECKED_CAST")
class FolderViewModelFactory(
        private val repository: FoldersRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FolderViewModel(repository) as T
    }
}