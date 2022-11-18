package com.rxyzent.remainder.core.adapter

import androidx.recyclerview.widget.RecyclerView
import com.rxyzent.remainder.R
import com.rxyzent.remainder.core.model.db.ListData
import com.rxyzent.remainder.databinding.ListItemBinding
import java.text.SimpleDateFormat

class ReminderListViewHolder(val binding: ListItemBinding):RecyclerView.ViewHolder(binding.root) {
    fun bindData(data:ListData){

        val simpleDateFormat = SimpleDateFormat("'['EEE']' dd.MM.yyyy '|' HH:mm ")
        val date = simpleDateFormat.format(data.date)
        val days = (data.date - System.currentTimeMillis())/(1000*60*60*24).toInt()

        binding.days.text = days.toString()
        binding.dateTime.text = date
        binding.title.text = data.title
        binding.description.text = data.description

        if(data.type.equals("todo")){
            binding.iconBack.setBackgroundResource(R.color.todo)
            binding.icon.setBackgroundResource(R.drawable.ic_baseline_sticky_note_2_24)
        }else if(data.type.equals("birthday")){
            binding.iconBack.setBackgroundResource(R.color.birthday)
            binding.icon.setBackgroundResource(R.drawable.ic_baseline_card_giftcard_24)
        }else if(data.type.equals("payment")){
            binding.iconBack.setBackgroundResource(R.color.payment)
            binding.icon.setBackgroundResource(R.drawable.ic_baseline_monetization_on_24)
        }


    }
}