package com.anshu.dynamicnotes.db.db1_for_folders

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FoldersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: FoldersEntity)

    @Delete
    suspend fun delete(item: FoldersEntity)
//    @Query("SELECT no_of_notes FROM folders_entity")
//    fun  getNumOfNotes(id:Int):Int
//
//    @Query("SELECT folder_lock FROM folders_entity")
//    fun  getFolderLock(id:Int):Boolean

    @Query("SELECT * FROM folders_entity")
    fun getAllFolders(): LiveData<List<FoldersEntity>>

    @Query("select * from folders_entity where id = :id")
    fun getFolderForNotes(id: Int?): FoldersEntity?
}