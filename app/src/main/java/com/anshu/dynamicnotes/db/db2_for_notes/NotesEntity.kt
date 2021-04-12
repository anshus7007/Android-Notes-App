package com.anshu.dynamicnotes.db.db2_for_notes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_entity")

data class NotesEntity (
    @ColumnInfo(name="folder_id")
    var folder_id:Int,
    @ColumnInfo(name="folder_name")
    var folder_name:String,
    @ColumnInfo(name="notes_name")
    var notes_name:String,

    @ColumnInfo(name="body")
    var body:String,
    @ColumnInfo(name="notes_time")
    var time:String,

    @ColumnInfo(name="no_of_notes")
    var num_of_notes:Int,

    @ColumnInfo(name="notes_lock")
    var notes_lock:Boolean,


        )
{
    @PrimaryKey(autoGenerate = true)
    var notes_id:Int?=null
}