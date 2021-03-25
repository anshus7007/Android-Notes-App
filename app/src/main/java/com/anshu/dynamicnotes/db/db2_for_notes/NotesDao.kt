package com.anshu.dynamicnotes.db.db2_for_notes

import androidx.lifecycle.LiveData
import androidx.room.*
import com.anshu.dynamicnotes.db.db1_for_folders.FoldersEntity

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: NotesEntity)

    @Delete
    suspend fun delete(item: NotesEntity)
//    @Query("SELECT no_of_notes FROM folders_entity")
//    fun  getNumOfNotes(id:Int):Int
//
//    @Query("SELECT folder_lock FROM folders_entity")
//    fun  getFolderLock(id:Int):Boolean

    @Query("SELECT * FROM notes_entity")
    fun getAllNotes(): LiveData<List<NotesEntity>>

    @Query("select * from notes_entity where folder_id = :id")
     fun getNoteById(id: Int): LiveData<List<NotesEntity>>
}