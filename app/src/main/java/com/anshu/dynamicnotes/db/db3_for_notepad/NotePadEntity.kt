package com.anshu.dynamicnotes.db.db3_for_notepad

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notepad_entity")

data class NotePadEntity (
    @ColumnInfo(name="note_id")
    var note_id:Int,
    @ColumnInfo(name="notepad_name")
    var notePad_name:String,

    @ColumnInfo(name="note_pad_data")
    var notepad_data:String,



    @ColumnInfo(name="notespad_lock")
    var notepad_lock:Boolean,


    )
{
    @PrimaryKey(autoGenerate = true)
    var notepad_id:Int?=null
}