package com.anshu.dynamicnotes.repository

import com.anshu.dynamicnotes.db.db1_for_folders.FoldersDatabase
import com.anshu.dynamicnotes.db.db1_for_folders.FoldersEntity

class FoldersRepository(private val db:FoldersDatabase) {
    suspend fun insert(item:FoldersEntity)=db.getFolderDao().insert(item)
    suspend fun delete(item: FoldersEntity)=db.getFolderDao().delete(item)
    fun getAllFolders()=db.getFolderDao().getAllFolders()

    fun getFolderById(id:Int)=db.getFolderDao().getFolderForNotes(id)
    suspend fun rename_folder(folder_id:Int,folders_name:String,currentDateAndTime:String,color_picker: String)=db.getFolderDao().rename_folder(folder_id,folders_name,currentDateAndTime,color_picker)
    suspend fun updateNumOfNotes(no_of_notes:String, fileId: Int)=db.getFolderDao().updateNumOfNotes(no_of_notes, fileId)

}