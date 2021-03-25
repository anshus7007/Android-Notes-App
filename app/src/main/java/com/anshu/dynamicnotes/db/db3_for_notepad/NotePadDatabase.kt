package com.anshu.dynamicnotes.db.db3_for_notepad

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anshu.dynamicnotes.db.db2_for_notes.NotesDao
import com.anshu.dynamicnotes.db.db2_for_notes.NotesEntity

@Database(
    entities = [NotePadEntity::class],
    version = 1)
abstract class NotePadDatabase: RoomDatabase() {
    abstract fun getNotepadDao() : NotePadDao

    //static in java
    //creates single instance at a time
    //database class is used at different places in app so there should only be one call from any part of app
    //therefore companion object is used
    companion object{
        @Volatile
        private var instance: NotePadDatabase?  = null

        private val LOCK = Any()
        //val db=CollectMoneyDatabase(this)::::invoke function will be called
        //if instance is null it'll create a new instance
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK)
        {
            //if instance is null createDatabase will be called
            instance ?: createDatabase(context).also {
                //result returned from createDatabase is stored in it
                instance =it
            }

        }
        //new instance will be create of this database class and result will be returned
        private  fun createDatabase(context: Context)=
            Room.databaseBuilder(context.applicationContext, NotePadDatabase::class.java,
                "NotePadDB.db").build()
    }

}