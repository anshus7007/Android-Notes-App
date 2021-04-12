package com.anshu.dynamicnotes.repository

import com.anshu.dynamicnotes.db.db1_for_folders.FoldersDatabase
import com.anshu.dynamicnotes.db.db1_for_folders.FoldersEntity
import com.anshu.dynamicnotes.db.db2_for_notes.NotesDatabase
import com.anshu.dynamicnotes.db.db2_for_notes.NotesEntity

class NotesRepository(private val db: NotesDatabase) {
    suspend fun insert_note(item: NotesEntity)=db.getNotesDao().insert(item)
    suspend fun delete_note(item: NotesEntity)=db.getNotesDao().delete(item)
    fun getAllNotes()=db.getNotesDao().getAllNotes()
     fun getNoteById(id:Int)=db.getNotesDao().getNoteById(id)
suspend    fun updateNote(title:String,body:String,time:String,id: Int) = db.getNotesDao().updateNotes(title,body,time,id)
    suspend    fun updateLock(notes_lock:Boolean, fileId: Int) = db.getNotesDao().updateLock(notes_lock, fileId)

}