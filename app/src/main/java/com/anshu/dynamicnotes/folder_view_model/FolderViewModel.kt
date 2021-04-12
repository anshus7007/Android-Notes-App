package com.anshu.dynamicnotes.folder_view_model

import androidx.lifecycle.ViewModel
import com.anshu.dynamicnotes.db.db1_for_folders.FoldersEntity
import com.anshu.dynamicnotes.repository.FoldersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FolderViewModel(private val repository:FoldersRepository):ViewModel() {

    fun insert(item:FoldersEntity)= CoroutineScope(Dispatchers.Main).launch {
        repository.insert(item)
    }


    fun delete(item:FoldersEntity)= CoroutineScope(Dispatchers.Main).launch {
        repository.delete(item)
    }
    fun getAllFolders()=repository.getAllFolders()
    fun rename_folder(folder_id:Int,folders_name:String,currentDateAndTime:String,color_picker: String) = CoroutineScope(Dispatchers.Main).launch {
        repository.rename_folder(folder_id,folders_name,currentDateAndTime,color_picker)
    }
    fun getFolderById(id:Int)=repository.getFolderById(id)

    fun updateNumOfNotes(no_of_notes:String, fileId: Int)= CoroutineScope(Dispatchers.Main).launch {
        repository.updateNumOfNotes(no_of_notes,fileId)
    }
}