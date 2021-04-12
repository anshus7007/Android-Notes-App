package com.anshu.dynamicnotes.notes_view_model

import androidx.lifecycle.ViewModel
import com.anshu.dynamicnotes.db.db2_for_notes.NotesEntity
import com.anshu.dynamicnotes.repository.NotesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NotesRepository): ViewModel() {

    fun insert(item: NotesEntity) = CoroutineScope(Dispatchers.Main).launch {
        repository.insert_note(item)
    }


    fun delete(item: NotesEntity) = CoroutineScope(Dispatchers.Main).launch {
        repository.delete_note(item)
    }

    fun updateLock(notes_lock:Boolean, fileId: Int) = CoroutineScope(Dispatchers.Main).launch {
        repository.updateLock(notes_lock,fileId)
    }
    fun getAllNotes() = repository.getAllNotes()
    fun getNoteById(id: Int) = repository.getNoteById(id)
    fun updateNote(title:String,body:String,time:String,id:Int) = CoroutineScope(Dispatchers.Main).launch {repository.updateNote(title,body,time,id)}

}