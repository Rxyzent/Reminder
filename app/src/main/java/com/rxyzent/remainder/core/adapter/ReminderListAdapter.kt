package com.rxyzent.remainder.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rxyzent.remainder.core.model.db.ListData
import com.rxyzent.remainder.databinding.ListItemBinding

class ReminderListAdapter:RecyclerView.Adapter<ReminderListViewHolder>() {

    var onListItemClick : ((data:ListData)->Unit)? = null

    val data = ArrayList<ListData>()

    fun setData(notes:List<ListData>){
        data.addAll(notes)
        notifyDataSetChanged()
    }
    fun addData(todo:ListData){
        data.add(todo)
        notifyItemInserted(data.size-1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderListViewHolder {
        return ReminderListViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ReminderListViewHolder, position: Int) {
        holder.bindData(data[position])
        holder.binding.root.setOnClickListener {
            onListItemClick?.invoke(data[position])
        }
    }

    override fun getItemCount(): Int = data.size
}