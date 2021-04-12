package com.anshu.dynamicnotes.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.anshu.dynamicnotes.R
import com.anshu.dynamicnotes.db.db1_for_folders.FoldersEntity
import com.anshu.dynamicnotes.db.db2_for_notes.NotesEntity
import com.anshu.dynamicnotes.notes_view_model.NoteViewModel
import com.anshu.dynamicnotes.ui.activity.NotePadActivity
import com.anshu.dynamicnotes.ui.activity.NotesActivity

class NotesRecyclerAdapter(val context: Context,
                           var items: List<NotesEntity>,
                           private val viewModel: NoteViewModel

) : RecyclerView.Adapter<NotesRecyclerAdapter.NotesViewHolder>() {

    private lateinit var listener: NotesRecyclerAdapter.Listener
    fun setListener(listener: NotesRecyclerAdapter.Listener) {
        this.listener = listener
    }
    fun removeAt(position: Int) {
        viewModel.delete(items[position])
//        items.removeAt(position)



//        if(size==0)
//            (context as Activity).findViewById<ConstraintLayout>(R.id.rl27).visibility=View.VISIBLE
//        else
//            (context as Activity).findViewById<ConstraintLayout>(R.id.rl27).visibility=View.GONE
//        (context as Activity).findViewById<MaterialCardView>(R.id.materialCardView25).setOnClickListener {
//            (context as Activity).startActivity(Intent(context, MainActivity::class.java))
//            finishAffinity(context)
//        }
        notifyDataSetChanged()
        notifyItemRemoved(position)
    }
    fun pinAt(position: Int) {
        items[position].notes_id?.let { viewModel.updateLock(true, it) }
//        items.removeAt(position)



//        if(size==0)
//            (context as Activity).findViewById<ConstraintLayout>(R.id.rl27).visibility=View.VISIBLE
//        else
//            (context as Activity).findViewById<ConstraintLayout>(R.id.rl27).visibility=View.GONE
//        (context as Activity).findViewById<MaterialCardView>(R.id.materialCardView25).setOnClickListener {
//            (context as Activity).startActivity(Intent(context, MainActivity::class.java))
//            finishAffinity(context)
//        }
        notifyDataSetChanged()
        notifyItemRemoved(position)
    }
    interface Listener {
        fun onOptionsClicked(notes: NotesEntity, note_id:Int)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.notes_single_row,
            parent,
            false
        )
        return NotesViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val currItem=items[position]

        if (currItem != null) {
            holder.notes_name.text=currItem!!.notes_name
        }
        if (currItem != null) {
            holder.time.text=currItem!!.time
        }
//        holder.num_of_notes.text=currItem.num_of_notes.toString()
//        holder.folder_card.setCardBackgroundColor(Color.parseColor(currItem.colorFolder))
            holder.itemView.setOnClickListener{
                if(currItem.notes_lock)
                {
                    currItem?.notes_id?.let { it1 -> listener.onOptionsClicked(currItem, it1) }

                }
                else {
                    val intent = Intent(context, NotePadActivity::class.java)
                    intent.putExtra("primary_key", currItem.notes_name)
                    intent.putExtra("id", currItem.notes_id)


                    intent.putExtra("folder_id_from_recycler", currItem.folder_id)
                    intent.putExtra("folder_name_from_recycler", currItem.folder_name)

                    intent.putExtra("body", currItem.body)

//                intent.putExtra("folder_id", currItem.id)
//                println("------------------------${currItem.id}-------------------------------}")
                    intent.putExtra("where", "noteUpdate")

                    context.startActivity(intent)
                }
            }
    }


    override fun getItemCount(): Int {
        return items.size
    }

    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val notes_name: TextView = itemView.findViewById(R.id.notes_name)
        val time: TextView = itemView.findViewById(R.id.time)
//        val menu: ImageView = itemView.findViewById(R.id.ivMenu)
//        val folder_card: MaterialCardView =itemView.findViewById(R.id.card_folder)
//        val num_of_notes: TextView = itemView.findViewById(R.id.no_of_notes)
    }
}