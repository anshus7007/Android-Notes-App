package com.anshu.dynamicnotes.note_pad_view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anshu.dynamicnotes.notes_view_model.NoteViewModel
import com.anshu.dynamicnotes.repository.NotePadRepaository
import com.anshu.dynamicnotes.repository.NotesRepository

@Suppress("UNCHECKED_CAST")

class NotePadViewModelFactory( private val repository: NotePadRepaository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NotePadViewModel(repository) as T
    }
}