package com.anshu.dynamicnotes.db.db1_for_folders

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "folders_entity")
data class FoldersEntity(
        @ColumnInfo(name="folders_name")
        var folder_name:String,

        @ColumnInfo(name="folders_date")
        var date:String,

        @ColumnInfo(name="no_of_notes")
        var num_of_notes:Int,

        @ColumnInfo(name="folder_lock")
        var folder_lock:Boolean,
        @ColumnInfo(name="color_picker")
        var colorFolder:String
){
@PrimaryKey(autoGenerate = true)
var id:Int?=null
}
