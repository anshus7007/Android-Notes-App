package com.anshu.dynamicnotes.ui.activity

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anshu.dynamicnotes.R
import com.anshu.dynamicnotes.adapter.FoldersRecyclerAdapter
import com.anshu.dynamicnotes.db.db1_for_folders.FoldersDatabase
import com.anshu.dynamicnotes.db.db1_for_folders.FoldersEntity
import com.anshu.dynamicnotes.folder_view_model.FolderViewModel
import com.anshu.dynamicnotes.folder_view_model.FolderViewModelFactory
import com.anshu.dynamicnotes.repository.FoldersRepository
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() ,FoldersRecyclerAdapter.Listener,FolderBottomSheet.Listener {
    lateinit var create_new_folder:ImageView
    lateinit var folder_recycler_view:RecyclerView
//    lateinit var txtCancel:TextView
//    lateinit var txtSave:TextView
var color_picker:String="#FFD52E"
    lateinit var selectedFolder:FoldersEntity
    var folder_Id_BottomSheet:Int=0


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val database= FoldersDatabase(this)
        val repository= FoldersRepository(database)
        val factory= FolderViewModelFactory(repository)
        val viewmodel = ViewModelProviders.of(this, factory).get(FolderViewModel::class.java)




        folder_recycler_view=findViewById(R.id.folder_recycler_view)
//        txtCancel=findViewById(R.id.txtcancel)
//        txtSave=findViewById(R.id.txtSave)
        create_new_folder=findViewById(R.id.create_new_folder)


        val adapter= FoldersRecyclerAdapter(this as Context, listOf(), viewmodel)
        val layoutManager=GridLayoutManager(this, 2)
        adapter.setListener(this)
        folder_recycler_view.layoutManager=layoutManager
        folder_recycler_view.adapter=adapter
        viewmodel.getAllFolders().observe(this, androidx.lifecycle.Observer {
            adapter.items = it
            adapter.notifyDataSetChanged()
        })





        create_new_folder.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val customLayout: View = layoutInflater.inflate(R.layout.new_folder_dialog, null)
            builder.setView(customLayout)
            val txtCancel:TextView=customLayout.findViewById(R.id.txtcancel)
            val txtSave:TextView=customLayout.findViewById(R.id.txtSave)
            val etFolderName:EditText=customLayout.findViewById(R.id.et_name_of_folder)
            val colorPicker:ImageView=customLayout.findViewById(R.id.color_picker)

            val dialog = builder.create()
            dialog.show()
            colorPicker.setOnClickListener {
                showColorPicker()
            }
        txtCancel.setOnClickListener(View.OnClickListener { v: View? -> dialog.dismiss() })

            txtSave.setOnClickListener(View.OnClickListener {
                val folder_name = etFolderName.text.toString()
                println("##############################${folder_name}###############")


                val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
                val currentDateAndTime: String = simpleDateFormat.format(Date())
                val folderEntity = FoldersEntity(folder_name, currentDateAndTime, 0, false, color_picker)
                viewmodel.insert(folderEntity)
                dialog.dismiss()
            })
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun showColorPicker() {
        ColorPickerDialogBuilder
                .with(this)
                .setTitle("Choose color")
                .initialColor(getColor(R.color.colorYellow))
                .setPositiveButton("ok")
                { _, selectedColor, _ -> color_picker="#"+Integer.toHexString(selectedColor).substring(2)


                }
                .build()
                .show()
    }

    override fun onOptionsClicked(folder: FoldersEntity,folder_id:Int) {
        selectedFolder=folder
        folder_Id_BottomSheet=folder_id
        val folderBottomSheet: FolderBottomSheet = FolderBottomSheet.newInstance(folder.folder_name)
        folderBottomSheet.setListener(this)
        folderBottomSheet.show(supportFragmentManager, "bottom_sheet")
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRenameClicked() {
        val database= FoldersDatabase(this)
        val repository= FoldersRepository(database)
        val factory= FolderViewModelFactory(repository)
        val viewmodel = ViewModelProviders.of(this, factory).get(FolderViewModel::class.java)


        val builder = AlertDialog.Builder(this)
        val customLayout: View = layoutInflater.inflate(R.layout.new_folder_dialog, null)
        builder.setView(customLayout)
        val txtCancel:TextView=customLayout.findViewById(R.id.txtcancel)
        val txtSave:TextView=customLayout.findViewById(R.id.txtSave)
        val etFolderName:EditText=customLayout.findViewById(R.id.et_name_of_folder)
        val colorPicker:ImageView=customLayout.findViewById(R.id.color_picker)
        customLayout.findViewById<TextView>(R.id.rename_folder).text="Rename folder"
        customLayout.findViewById<TextView>(R.id.rename_folder_sub).text="Enter a new name for this folder."

        customLayout.findViewById<TextView>(R.id.txtSave).text="Update folder"


        val dialog = builder.create()
        dialog.show()
        colorPicker.setOnClickListener {
            showColorPicker()
        }
        txtCancel.setOnClickListener(View.OnClickListener { v: View? -> dialog.dismiss() })

        txtSave.setOnClickListener(View.OnClickListener {
            val folder_name = etFolderName.text.toString()
            println("##############################${folder_name}###############")


            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
            val currentDateAndTime: String = simpleDateFormat.format(Date())
//            val folderEntity = FoldersEntity()
            viewmodel.rename_folder(folder_Id_BottomSheet,folder_name, currentDateAndTime, color_picker)
            dialog.dismiss()
        })
    }

    override fun onDeleteClicked() {
        val database= FoldersDatabase(this)
        val repository= FoldersRepository(database)
        val factory= FolderViewModelFactory(repository)
        val viewmodel = ViewModelProviders.of(this, factory).get(FolderViewModel::class.java)

        viewmodel.delete(selectedFolder)
    }

//    override fun onShareClicked() {
//        TODO("Not yet implemented")
//    }

//    override fun onFolderLockClicked() {
//        TODO("Not yet implemented")
//    }

}