package com.anshu.dynamicnotes.repository

import com.anshu.dynamicnotes.db.db2_for_notes.NotesDatabase
import com.anshu.dynamicnotes.db.db2_for_notes.NotesEntity
import com.anshu.dynamicnotes.db.db3_for_notepad.NotePadDatabase
import com.anshu.dynamicnotes.db.db3_for_notepad.NotePadEntity

class NotePadRepaository(private val db: NotePadDatabase) {
    suspend fun insert_note(item: NotePadEntity) = db.getNotepadDao().insert(item)
    suspend fun delete_note(item: NotePadEntity) = db.getNotepadDao().delete(item)
    fun getAllNotePad() = db.getNotepadDao().getAllNotePad()
    fun getAllNotePadById(id: Int) = db.getNotepadDao().getNotePadById(id)
}