package com.anshu.dynamicnotes.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
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


class NotesActivity : AppCompatActivity() {
    lateinit var create_new_note:ImageView
    lateinit var folder_name_notes: TextView
    lateinit var inside_folder_recycler_view:RecyclerView
    lateinit var folder_id_txt:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)



        val folder_name=intent.getStringExtra("folder_name")
        val folder_id=intent.getIntExtra("folder_id",0)
        val folderId=intent.getIntExtra("folderId",-1)
        val folder_name_from_notes=intent.getStringExtra("folder_name_from_notes")
        val from=intent.getStringExtra("from")

        folder_id_txt=findViewById(R.id.folder_id)
        inside_folder_recycler_view=findViewById(R.id.inside_folder_recycler_view)
        create_new_note=findViewById(R.id.create_new_note_inside_folder)
        folder_name_notes=findViewById(R.id.folder_name_notes)



        val database= NotesDatabase(this!!)
        val repository= NotesRepository(database)
        val factory= NoteViewModelFactory(repository)
        val viewmodel = ViewModelProviders.of(this, factory).get(NoteViewModel::class.java)
        val adapter= NotesRecyclerAdapter(this as Context, listOf(), viewmodel)
        val layoutManager= LinearLayoutManager(this)
        inside_folder_recycler_view.layoutManager=layoutManager
        layoutManager.stackFromEnd=true
        inside_folder_recycler_view.adapter=adapter

        folder_id_txt.text=folder_id.toString()




        if(from.equals("notepad")) {
            folder_name_notes.text = folder_name_from_notes
            folder_id_txt.text= folderId.toString()
            viewmodel.getNoteById(folderId).observe(this, androidx.lifecycle.Observer {
                adapter.items= it
                adapter.notifyDataSetChanged()
            })
        }
        if(from.equals("folder")) {
            folder_name_notes.text = folder_name
            folder_id_txt.text = folder_id.toString()
            viewmodel.getNoteById(folder_id).observe(this, androidx.lifecycle.Observer {
                adapter.items= it
                adapter.notifyDataSetChanged()
            })
        }


        create_new_note.setOnClickListener {
            val i= Intent(this,NotePadActivity::class.java)
            i.putExtra("folder_name",folder_name_notes.text)
            i.putExtra("folder_id", (folder_id_txt.text as String).toInt())

            startActivity(i)
            finish()

        }

    }




}