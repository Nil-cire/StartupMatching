package com.eric.startupmatching.team.information

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eric.startupmatching.data.Event

//class TeamInformationAdapter(val onClickListener: OnClickListener) : ListAdapter<Event, RecyclerView.ViewHolder>(CategoryDiffCallback) {
//
//    class ViewHolder(var binding: ItemTaskTodoPropareRecyclerViewBinding):
//        RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(todo: Event) {
//            binding.event = event
//            binding.executePendingBindings()
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        val layoutInflater = LayoutInflater.from(parent.context)
//        val binding = ItemTaskTodoPropareRecyclerViewBinding.inflate(layoutInflater, parent, false)
//        return ViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//
////        val item = getItem(position)
////        holder.bind(item)
//
//        if (holder is ViewHolder) {
//            val task = getItem(position)
//            holder.itemView.setOnClickListener {
//                onClickListener.onClick(task)
//            }
//            holder.bind(task)
//        } else {
//            Log.d("Boooo", "cant bind data")
//        }
//
//    }
//
//    companion object CategoryDiffCallback : DiffUtil.ItemCallback<Event>() {
//        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
//            return oldItem === newItem
//        }
//
//        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
//            return oldItem.id == newItem.id
//        }
//    }
//
//    class OnClickListener(val clickListener: (event: Event) -> Unit) {
//        fun onClick(event: Event) = clickListener(event)
//    }
//}