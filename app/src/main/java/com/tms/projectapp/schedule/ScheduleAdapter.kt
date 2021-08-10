package com.tms.projectapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tms.projectapp.database.Data
import com.tms.projectapp.databinding.ItemSheduleListBinding
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*

class ScheduleAdapter(
    private val click: (Data) -> Unit
): ListAdapter<Data, ScheduleViewHolder>(DifUtilItemCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        return ScheduleViewHolder(
            click,
            ItemSheduleListBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}

class DifUtilItemCallBack: DiffUtil.ItemCallback<Data>() {
    override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
        return oldItem.day == newItem.day && oldItem.name == newItem.name
                && oldItem.week == newItem.week && oldItem.time == newItem.time
    }
}

class ScheduleViewHolder(
    private val click: (Data) -> Unit,
    private val binding: ItemSheduleListBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Data) {
        binding.tvDay.text = item.day
        binding.tvWeek.text = item.week
        binding.tvLesson.text = item.name
        binding.tvTime.text = item.time

        itemView.setOnClickListener {
            click(item)
        }

    }

    companion object {
        private val dateFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())

    }
}