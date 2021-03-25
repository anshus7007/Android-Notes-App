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

    fun getFolderById(id:Int)=repository.getFolderById(id)
}