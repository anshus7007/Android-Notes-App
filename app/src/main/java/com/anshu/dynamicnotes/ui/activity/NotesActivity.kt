package com.anshu.dynamicnotes.ui.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anshu.dynamicnotes.R
import com.anshu.dynamicnotes.adapter.NotesRecyclerAdapter
import com.anshu.dynamicnotes.db.db1_for_folders.FoldersDatabase
import com.anshu.dynamicnotes.db.db1_for_folders.FoldersEntity
import com.anshu.dynamicnotes.db.db2_for_notes.NotesDatabase
import com.anshu.dynamicnotes.db.db2_for_notes.NotesEntity
import com.anshu.dynamicnotes.folder_view_model.FolderViewModel
import com.anshu.dynamicnotes.folder_view_model.FolderViewModelFactory
import com.anshu.dynamicnotes.notes_view_model.NoteViewModel
import com.anshu.dynamicnotes.notes_view_model.NoteViewModelFactory
import com.anshu.dynamicnotes.repository.FoldersRepository
import com.anshu.dynamicnotes.repository.NotesRepository
import com.anshu.dynamicnotes.ui.SwipeToDeleteCallback
import com.anshu.dynamicnotes.ui.SwipeToDeleteCallback1
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*


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
                findViewById<TextView>(R.id.num_of_notes_inside_folder).text=it.size.toString()

            })

        }
        if(from.equals("folder")) {
            folder_name_notes.text = folder_name
            folder_id_txt.text = folder_id.toString()
            viewmodel.getNoteById(folder_id).observe(this, androidx.lifecycle.Observer {
                adapter.items= it
                adapter.notifyDataSetChanged()
                findViewById<TextView>(R.id.num_of_notes_inside_folder).text=it.size.toString()

            })

        }
        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val adapter = inside_folder_recycler_view.adapter as NotesRecyclerAdapter
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(inside_folder_recycler_view)

//        val swipeHandler1 = object : SwipeToDeleteCallback1(this) {
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//
//                val adapter = inside_folder_recycler_view.adapter as NotesRecyclerAdapter
//                adapter.pinAt(viewHolder.adapterPosition)
//                val builder = AlertDialog.Builder(this)
//                val customLayout: View = layoutInflater.inflate(R.layout.item_notes_password, null)
//                builder.setView(customLayout)
//                val txtCancel:TextView=customLayout.findViewById(R.id.txtcancel)
//                val txtSave:TextView=customLayout.findViewById(R.id.txtSave)
//                val etFolderName: EditText =customLayout.findViewById(R.id.et_name_of_folder)
//                val colorPicker:ImageView=customLayout.findViewById(R.id.color_picker)
//
//                val dialog = builder.create()
//                dialog.show()
//
//                txtCancel.setOnClickListener(View.OnClickListener { v: View? -> dialog.dismiss() })
//
//                txtSave.setOnClickListener(View.OnClickListener {
//                    val folder_name = etFolderName.text.toString()
//                    println("##############################${folder_name}###############")
//
//
//                    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
//                    val currentDateAndTime: String = simpleDateFormat.format(Date())
//                    val folderEntity = FoldersEntity(folder_name, currentDateAndTime, 0, false, color_picker)
//                    viewmodel.insert(folderEntity)
//                    dialog.dismiss()
//                })
//            }
//        }
//        val itemTouchHelper1 = ItemTouchHelper(swipeHandler1)
//        itemTouchHelper1.attachToRecyclerView(inside_folder_recycler_view)
//
        create_new_note.setOnClickListener {
            val i= Intent(this,NotePadActivity::class.java)
            i.putExtra("folder_name",folder_name_notes.text)
            i.putExtra("folder_id", (folder_id_txt.text as String).toInt())
            i.putExtra("where", "create_new_note")
            startActivity(i)
            finish()

        }
        findViewById<TextView>(R.id.backToFolders).setOnClickListener {
            val i= Intent(this,MainActivity::class.java)
            val database= FoldersDatabase(this!!)
            val repository= FoldersRepository(database)
            val factory= FolderViewModelFactory(repository)
            val viewmodel = ViewModelProviders.of(this, factory).get(FolderViewModel::class.java)
            viewmodel.updateNumOfNotes(findViewById<TextView>(R.id.num_of_notes_inside_folder).text.toString(),(findViewById<TextView>(R.id.folder_id).text.toString()).toInt())

            startActivity(i)
            finish()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val i= Intent(this,MainActivity::class.java)
        val database= FoldersDatabase(this!!)
        val repository= FoldersRepository(database)
        val factory= FolderViewModelFactory(repository)
        val viewmodel = ViewModelProviders.of(this, factory).get(FolderViewModel::class.java)
        viewmodel.updateNumOfNotes(findViewById<TextView>(R.id.num_of_notes_inside_folder).text.toString(),(findViewById<TextView>(R.id.folder_id).text.toString()).toInt())

        startActivity(i)
        finish()
    }




}