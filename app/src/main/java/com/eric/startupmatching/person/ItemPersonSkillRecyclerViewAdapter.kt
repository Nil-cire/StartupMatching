package com.eric.startupmatching.person

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eric.startupmatching.databinding.ItemPersonMainSkillRecyclerViewBinding


class ItemPersonSkillRecyclerViewAdapter(val onClickListener: OnClickListener) : ListAdapter<String, RecyclerView.ViewHolder>(CategoryDiffCallback) {

    class ViewHolder(var binding: ItemPersonMainSkillRecyclerViewBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(skill: String) {
            binding.skill = skill
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPersonMainSkillRecyclerViewBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ViewHolder) {
            val skill = getItem(position)
            holder.itemView.setOnClickListener {
                onClickListener.onClick(skill)
            }
            holder.bind(skill)
        } else {
            Log.d("Boooo", "cant bind data")
        }

    }

    companion object CategoryDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem== newItem
        }
    }

    class OnClickListener(val clickListener: (skill: String) -> Unit) {
        fun onClick(skill: String) = clickListener(skill)
    }
}