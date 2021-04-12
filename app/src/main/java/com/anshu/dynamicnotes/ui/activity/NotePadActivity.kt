package com.anshu.dynamicnotes.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.media.Image
import android.os.Bundle
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anshu.dynamicnotes.R
import com.anshu.dynamicnotes.adapter.NotesRecyclerAdapter
import com.anshu.dynamicnotes.db.db2_for_notes.NotesDatabase
import com.anshu.dynamicnotes.db.db2_for_notes.NotesEntity
import com.anshu.dynamicnotes.db.db3_for_notepad.NotePadDatabase
import com.anshu.dynamicnotes.db.db3_for_notepad.NotePadEntity
import com.anshu.dynamicnotes.note_pad_view_model.NotePadViewModel
import com.anshu.dynamicnotes.note_pad_view_model.NotePadViewModelFactory
import com.anshu.dynamicnotes.notes_view_model.NoteViewModel
import com.anshu.dynamicnotes.notes_view_model.NoteViewModelFactory
import com.anshu.dynamicnotes.repository.NotePadRepaository
import com.anshu.dynamicnotes.repository.NotesRepository
import java.util.*


class NotePadActivity : AppCompatActivity() ,NotepadBottomSheet.Listener{

    private var value: String? =null
    lateinit var fromWhere:String
     var Id:Int=-1
     var folderId:Int=-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_note_pad)

            val folder_id = intent!!.getIntExtra("folder_id",-1)
            value = intent!!.getStringExtra("folder_name")
            val update=intent.getStringExtra("where")
            val notes_name=intent.getStringExtra("primary_key")
            val id=intent.getIntExtra("id",-1)
            var notePadBody=intent.getStringExtra("body")
            var folder_id_from_recycler=intent.getIntExtra("folder_id_from_recycler",-1)
            var folder_name_from_recycler=intent.getStringExtra("folder_name_from_recycler")

            fromWhere=update.toString()
        Id=id
        folderId=folder_id

        if(update.equals("noteUpdate")) {
            findViewById<TextView>(R.id.folder_id_notePad).text = folder_id_from_recycler.toString()
            findViewById<TextView>(R.id.folder_name_note_pad).text = folder_name_from_recycler

        }
        if(update.equals("create_new_note")) {
            findViewById<TextView>(R.id.folder_id_notePad).text = folder_id.toString()
            findViewById<TextView>(R.id.folder_name_note_pad).text = value

        }



        val database= NotesDatabase(this!!)
        val repository= NotesRepository(database)
        val factory= NoteViewModelFactory(repository)
        val viewmodel = ViewModelProviders.of(this, factory).get(NoteViewModel::class.java)
        val adapter= NotesRecyclerAdapter(this as Context, listOf(), viewmodel)


        val db= NotePadDatabase(this!!)
        val repo_note_pad= NotePadRepaository(db)
        val factory_note_pad= NotePadViewModelFactory(repo_note_pad)
        val viewmodel_note_pad = ViewModelProviders.of(this, factory_note_pad).get(NotePadViewModel::class.java)



        if(update.equals("noteUpdate"))
        {
            findViewById<TextView>(R.id.folder_name_note_pad).text = folder_name_from_recycler

            findViewById<TextView>(R.id.notes_title).text= notes_name
            println("@!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!11111111${notes_name}11111222222222222222222222222222222222222222")
            findViewById<TextView>(R.id.body).text= notePadBody


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

                val body=findViewById<EditText>(R.id.body).text.toString()
//                val noteEntity= NotesEntity( folder_id,title,body, time, 0, false)
                viewmodel.updateNote(title,body,time,id)

//                val notePadEntity= NotePadEntity( title,title,time, body, false)
                viewmodel_note_pad.updateNote(title,body)


                val i = Intent(this, NotesActivity::class.java)
                i.putExtra("folder_name_from_notes",findViewById<TextView>(R.id.folder_name_note_pad).text)
                i.putExtra("folderId",(findViewById<TextView>(R.id.folder_id_notePad).text as String).toInt())
                i.putExtra("from","notepad")


                viewmodel.getNoteById(folder_id).observe(this, androidx.lifecycle.Observer {
                    adapter.items= it
                    adapter.notifyDataSetChanged()
                })


                startActivity(i)
                finish()
//                (this as Activity?)!!.overridePendingTransition(0, 0)

            }
        }

if(update.equals("create_new_note"))
{
        findViewById<ImageView>(R.id.back_btn).setOnClickListener {
            val title = findViewById<EditText>(R.id.notes_title).text.toString()
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)
            val ampm = c.get(Calendar.AM_PM)
            var am_pm: String = ""
            if (ampm == 0)
                am_pm = "AM"
            else
                am_pm = "PM"
            val time = "$hour:$minute $am_pm"

            val body = findViewById<EditText>(R.id.body).text.toString()
            val noteEntity = value?.let { it1 -> NotesEntity(folder_id, it1, title, body, time, 0, false) }
            if (noteEntity != null) {
                viewmodel.insert(noteEntity)
            }

            val notePadEntity = NotePadEntity(title, title, time, body, false)
            viewmodel_note_pad.insert(notePadEntity)


            val i = Intent(this, NotesActivity::class.java)
            i.putExtra("folder_name_from_notes", findViewById<TextView>(R.id.folder_name_note_pad).text)
            i.putExtra("folderId", (findViewById<TextView>(R.id.folder_id_notePad).text as String).toInt())
            i.putExtra("from", "notepad")


            viewmodel.getNoteById(folder_id).observe(this, androidx.lifecycle.Observer {
                adapter.items = it
                adapter.notifyDataSetChanged()
            })


            startActivity(i)
            finish()
//            (this as Activity?)!!.overridePendingTransition(0, 0)
        }
        }

        findViewById<ImageView>(R.id.bottom_edit).setOnClickListener {
            val notepadBottomSheet: NotepadBottomSheet = NotepadBottomSheet.newInstance()
            notepadBottomSheet.setListener(this)
            notepadBottomSheet.show(supportFragmentManager, "bottom_sheet_notepad")
        }


//        findViewById<ConstraintLayout>(R.id.rootView).viewTreeObserver.addOnGlobalLayoutListener {
//            val rec = Rect()
//            findViewById<ConstraintLayout>(R.id.rootView).getWindowVisibleDisplayFrame(rec)
//
//            //finding screen height
//            val screenHeight = findViewById<ConstraintLayout>(R.id.rootView).rootView.height
//
//            //finding keyboard height
//            val keypadHeight = screenHeight - rec.bottom
//
//            if (keypadHeight > screenHeight * 0.15) {
//                Toast.makeText(this, "VISIBLE KEYBOARD", Toast.LENGTH_LONG).show()
//            } else {
//                Toast.makeText(this, "NO KEYBOARD", Toast.LENGTH_LONG).show()
//            }
//        }

    }

    override fun onBackPressed() {

        super.onBackPressed()
//        val folder_id = intent!!.getIntExtra("folder_id",-1)
//
//        val i =Intent(this,NotesActivity::class.java)
//        i.putExtra("folder_name_from_notes",value)
//        i.putExtra("from","notepad")
//        startActivity(i)
//        finish()

//        val folder_id = intent!!.getIntExtra("folder_id",-1)
//        value = intent!!.getStringExtra("folder_name")
//        val update=intent.getStringExtra("where")
//        val notes_name=intent.getStringExtra("primary_key")
//        val id=intent.getIntExtra("id",-1)
//        var notePadBody=intent.getStringExtra("body")
//        var folder_id_from_recycler=intent.getIntExtra("folder_id_from_recycler",-1)
//        var folder_name_from_recycler=intent.getStringExtra("folder_name_from_recycler")
//        println("####################################${update}#######################################")
//        if(update.equals("noteUpdate")) {
//            findViewById<TextView>(R.id.folder_id_notePad).text = folder_id_from_recycler.toString()
//            findViewById<TextView>(R.id.folder_name_note_pad).text = folder_name_from_recycler
//
//        }
//        if(update.equals("create_new_note")) {
//            findViewById<TextView>(R.id.folder_id_notePad).text = folder_id.toString()
//            findViewById<TextView>(R.id.folder_name_note_pad).text = value
//
//        }



        val database= NotesDatabase(this!!)
        val repository= NotesRepository(database)
        val factory= NoteViewModelFactory(repository)
        val viewmodel = ViewModelProviders.of(this, factory).get(NoteViewModel::class.java)
        val adapter= NotesRecyclerAdapter(this as Context, listOf(), viewmodel)


        val db= NotePadDatabase(this!!)
        val repo_note_pad= NotePadRepaository(db)
        val factory_note_pad= NotePadViewModelFactory(repo_note_pad)
        val viewmodel_note_pad = ViewModelProviders.of(this, factory_note_pad).get(NotePadViewModel::class.java)



        if(fromWhere.equals("noteUpdate"))
        {
//            findViewById<TextView>(R.id.folder_name_note_pad).text = folder_name_from_recycler
//
//            findViewById<TextView>(R.id.notes_title).text= notes_name
//            println("@!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!11111111${notes_name}11111222222222222222222222222222222222222222")
//            findViewById<TextView>(R.id.body).text= notePadBody


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

                val body=findViewById<EditText>(R.id.body).text.toString()
//                val noteEntity= NotesEntity( folder_id,title,body, time, 0, false)
                viewmodel.updateNote(title,body,time,Id)

//                val notePadEntity= NotePadEntity( title,title,time, body, false)
                viewmodel_note_pad.updateNote(title,body)


                val i = Intent(this, NotesActivity::class.java)
                i.putExtra("folder_name_from_notes",findViewById<TextView>(R.id.folder_name_note_pad).text)
                i.putExtra("folderId",(findViewById<TextView>(R.id.folder_id_notePad).text as String).toInt())
                i.putExtra("from","notepad")


                viewmodel.getNoteById(folderId).observe(this, androidx.lifecycle.Observer {
                    adapter.items= it
                    adapter.notifyDataSetChanged()
                })


                startActivity(i)
                finish()
//                (this as Activity?)!!.overridePendingTransition(0, 0)


        }

        if(fromWhere.equals("create_new_note"))
        {
                val title = findViewById<EditText>(R.id.notes_title).text.toString()
                val c = Calendar.getInstance()
                val hour = c.get(Calendar.HOUR_OF_DAY)
                val minute = c.get(Calendar.MINUTE)
                val ampm = c.get(Calendar.AM_PM)
                var am_pm: String = ""
                if (ampm == 0)
                    am_pm = "AM"
                else
                    am_pm = "PM"
                val time = "$hour:$minute $am_pm"

                val body = findViewById<EditText>(R.id.body).text.toString()
                val noteEntity = value?.let { it1 -> NotesEntity(folderId, it1, title, body, time, 0, false) }
                if (noteEntity != null) {
                    viewmodel.insert(noteEntity)
                }

                val notePadEntity = NotePadEntity(title, title, time, body, false)
                viewmodel_note_pad.insert(notePadEntity)


                val i = Intent(this, NotesActivity::class.java)
                i.putExtra("folder_name_from_notes", findViewById<TextView>(R.id.folder_name_note_pad).text)
                i.putExtra("folderId", (findViewById<TextView>(R.id.folder_id_notePad).text as String).toInt())
                i.putExtra("from", "notepad")


                viewmodel.getNoteById(folderId).observe(this, androidx.lifecycle.Observer {
                    adapter.items = it
                    adapter.notifyDataSetChanged()
                })


                startActivity(i)
                finish()
//            (this as Activity?)!!.overridePendingTransition(0, 0)

        }

    }




}