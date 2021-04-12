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


    @Query("update folders_entity set no_of_notes = :no_of_notes where id = :fileId")
    suspend fun updateNumOfNotes(no_of_notes:String, fileId: Int)

    @Query("update folders_entity set folders_name = :folders_name,folders_date= :currentDateAndTime,color_picker=:color_picker where id = :folder_id")
    suspend fun rename_folder(folder_id:Int,folders_name:String,currentDateAndTime:String,color_picker: String)
}