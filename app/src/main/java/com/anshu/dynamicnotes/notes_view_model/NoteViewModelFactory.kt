package com.anshu.dynamicnotes.notes_view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anshu.dynamicnotes.folder_view_model.FolderViewModel
import com.anshu.dynamicnotes.repository.FoldersRepository
import com.anshu.dynamicnotes.repository.NotesRepository

@Suppress("UNCHECKED_CAST")

class NoteViewModelFactory( private val repository: NotesRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NoteViewModel(repository) as T
    }
}