package com.anshu.dynamicnotes.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anshu.dynamicnotes.R
import com.anshu.dynamicnotes.adapter.NotesRecyclerAdapter
import com.anshu.dynamicnotes.db.db2_for_notes.NotesDatabase
import com.anshu.dynamicnotes.db.db2_for_notes.NotesEntity
import com.anshu.dynamicnotes.notes_view_model.NoteViewModel
import com.anshu.dynamicnotes.notes_view_model.NoteViewModelFactory
import com.anshu.dynamicnotes.repository.NotesRepository
import java.util.*


class NotePadActivity : AppCompatActivity() {

    private var value: String? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_note_pad)

        val folder_id = intent!!.getIntExtra("folder_id",-1)
        value = intent!!.getStringExtra("folder_name")


        val database= NotesDatabase(this!!)
        val repository= NotesRepository(database)
        val factory= NoteViewModelFactory(repository)
        val viewmodel = ViewModelProviders.of(this, factory).get(NoteViewModel::class.java)
        val adapter= NotesRecyclerAdapter(this as Context, listOf(), viewmodel)


        findViewById<TextView>(R.id.folder_name_note_pad).text=value



        findViewById<ImageView>(R.id.back_btn).setOnClickListener {
            val title=findViewById<EditText>(R.id.notes_title).text.toString()
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)
            val ampm=c.get(Calendar.AM_PM)
            var am_pm:String=""
            if(ampm==0)
                am_pm="AM"
            else
                am_pm="PM"
            val time= "$hour:$minute $am_pm"


            val noteEntity= NotesEntity( folder_id,title, time, 0, false)
            viewmodel.insert(noteEntity)


            val i = Intent(this, NotesActivity::class.java)
            i.putExtra("folder_name_from_notes",value)
            i.putExtra("folderId",folder_id)
            i.putExtra("from","notepad")


            viewmodel.getNoteById(folder_id).observe(this, androidx.lifecycle.Observer {
                adapter.items= it
                adapter.notifyDataSetChanged()
            })


            startActivity(i)
            finish()
            (this as Activity?)!!.overridePendingTransition(0, 0)

        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val i =Intent(this,NotesActivity::class.java)
        i.putExtra("folder_name_from_notes",value)
        i.putExtra("from","notepad")
        startActivity(i)
        finish()
    }




}