package com.anshu.dynamicnotes.db.db3_for_notepad

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

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
    suspend fun getNotePadById(id: String?): NotePadEntity?

    @Query("select notepad_name from notepad_entity where notepad_id = :id")
    suspend fun getNotePadTitle(id: String): String

    @Query("select note_pad_data from notepad_entity where notepad_id = :id")
    suspend fun getNotePadBody(id: String): String

    @Query("update notepad_entity set time = :time where notepad_id = :fileId")
    suspend fun updateNote(time: String, fileId: String)
}