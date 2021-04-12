package com.anshu.dynamicnotes.repository

import com.anshu.dynamicnotes.db.db2_for_notes.NotesDatabase
import com.anshu.dynamicnotes.db.db2_for_notes.NotesEntity
import com.anshu.dynamicnotes.db.db3_for_notepad.NotePadDatabase
import com.anshu.dynamicnotes.db.db3_for_notepad.NotePadEntity

class NotePadRepaository(private val db: NotePadDatabase) {
    suspend fun insert_note(item: NotePadEntity) = db.getNotepadDao().insert(item)
    suspend fun delete_note(item: NotePadEntity) = db.getNotepadDao().delete(item)
    fun getAllNotePad() = db.getNotepadDao().getAllNotePad()
     suspend fun getAllNotePadById(id: String) = db.getNotepadDao().getNotePadById(id)
    suspend fun updateNote(time:String,id: String) = db.getNotepadDao().updateNote(time,id)
    suspend fun getNotePadTitle(id: String):String = db.getNotepadDao().getNotePadTitle(id)
    suspend fun getNotePadBody(id: String):String = db.getNotepadDao().getNotePadBody(id)

}