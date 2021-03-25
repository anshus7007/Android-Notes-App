package com.anshu.dynamicnotes.db.db3_for_notepad

import androidx.lifecycle.LiveData
import androidx.room.*
import com.anshu.dynamicnotes.db.db2_for_notes.NotesEntity

@Dao

interface NotePadDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: NotePadEntity)

    @Delete
    suspend fun delete(item: NotePadEntity)
//    @Query("SELECT no_of_notes FROM folders_entity")
//    fun  getNumOfNotes(id:Int):Int
//
//    @Query("SELECT folder_lock FROM folders_entity")
//    fun  getFolderLock(id:Int):Boolean

    @Query("SELECT * FROM notepad_entity")
    fun getAllNotePad(): LiveData<List<NotePadEntity>>

    @Query("select * from notepad_entity where notepad_id = :id")
    fun getNotePadById(id: Int?): NotePadEntity?
}