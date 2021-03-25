package com.anshu.dynamicnotes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anshu.dynamicnotes.R
import com.anshu.dynamicnotes.db.db2_for_notes.NotesEntity
import com.anshu.dynamicnotes.notes_view_model.NoteViewModel

class NotesRecyclerAdapter(val context: Context,
                           var items: List<NotesEntity>,
                           private val viewModel: NoteViewModel

) : RecyclerView.Adapter<NotesRecyclerAdapter.NotesViewHolder>() {

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