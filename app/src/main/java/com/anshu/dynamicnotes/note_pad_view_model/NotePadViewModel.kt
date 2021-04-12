package com.anshu.dynamicnotes.note_pad_view_model

import androidx.lifecycle.ViewModel
import com.anshu.dynamicnotes.db.db2_for_notes.NotesEntity
import com.anshu.dynamicnotes.db.db3_for_notepad.NotePadEntity
import com.anshu.dynamicnotes.repository.NotePadRepaository
import com.anshu.dynamicnotes.repository.NotesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NotePadViewModel(private val repository: NotePadRepaository): ViewModel() {

    fun insert(item: NotePadEntity) = CoroutineScope(Dispatchers.Main).launch {
        repository.insert_note(item)
    }


    fun delete(item: NotePadEntity) = CoroutineScope(Dispatchers.Main).launch {
        repository.delete_note(item)
    }

    fun getAllNotepad() = repository.getAllNotePad()
    fun getNotePadById(id: String) = CoroutineScope(Dispatchers.Main).launch {repository.getAllNotePadById(id)}

    fun updateNote(time:String,id:String) = CoroutineScope(Dispatchers.Main).launch {
        repository.updateNote(time,id)
    }
    fun getNotePadTitle(id:String): Job = CoroutineScope(Dispatchers.Main).launch {repository.getNotePadTitle(id)}
    fun getNotePadBody(id:String): Job = CoroutineScope(Dispatchers.Main).launch {repository.getNotePadBody(id)}

}