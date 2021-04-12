package com.anshu.dynamicnotes.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.anshu.dynamicnotes.R
import com.anshu.dynamicnotes.db.db1_for_folders.FoldersEntity
import com.anshu.dynamicnotes.folder_view_model.FolderViewModel
import com.anshu.dynamicnotes.ui.activity.MainActivity
import com.anshu.dynamicnotes.ui.activity.NotesActivity
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.card.MaterialCardView

class FoldersRecyclerAdapter(val context: Context,
                             var items: List<FoldersEntity>,
                             private val viewModel: FolderViewModel

) : RecyclerView.Adapter<FoldersRecyclerAdapter.FolderViewHolder>() {

     private lateinit var listener:Listener
    fun setListener(listener: Listener) {
        this.listener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
                R.layout.folder_single_row,
                parent,
                false
        )
        return FolderViewHolder(view)
    }

    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {
        val currItem=items[position]

        holder.folder_name.text=currItem.folder_name
        holder.folder_date.text=currItem.date
        holder.num_of_notes.text=currItem.num_of_notes.toString()
        holder.folder_card.setCardBackgroundColor(Color.parseColor(currItem.colorFolder))
        holder.itemView.setOnClickListener{
            val intent=Intent(context, NotesActivity::class.java)
            intent.putExtra("folder_name", currItem.folder_name)
            intent.putExtra("folder_id", currItem.id)
            println("------------------------${currItem.id}-------------------------------}")
            intent.putExtra("from", "folder")


            context.startActivity(intent)

        }
        holder.menu.setOnClickListener {
            currItem?.id?.let { it1 -> listener.onOptionsClicked(currItem, it1) }
            }


    }
    interface Listener {
        fun onOptionsClicked(folder: FoldersEntity,folder_id:Int)
    }

    override fun getItemCount(): Int {
        return items.size
    }

     class FolderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val folder_name: TextView = itemView.findViewById(R.id.folder_name)
        val folder_date: TextView = itemView.findViewById(R.id.date_folder)
        val menu: View = itemView.findViewById(R.id.options)
        val folder_card:MaterialCardView   =itemView.findViewById(R.id.card_folder)
        val num_of_notes: TextView = itemView.findViewById(R.id.no_of_notes)
//         val bottom_sheet:ConstraintLayout=itemView.findViewById(R.id.bottomSheet)
//        init {
//            itemView.findViewById<ImageView>(R.id.ivMenu).setOnClickListener {
//                fun onClick(v: View?) {
//                    listener.onOptionsClicked(items.get(adapterPosition))
//                }
//            }
//        }

    }
}