package com.eric.startupmatching.person.detail

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eric.startupmatching.data.Achievement
import com.eric.startupmatching.databinding.ItemPersonDetailAchievementBinding
class PersonDetailAchievementAdapter(val onClickListener: OnClickListener) : ListAdapter<Achievement, RecyclerView.ViewHolder>(CategoryDiffCallback) {

    class ViewHolder(var binding: ItemPersonDetailAchievementBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(achievement: Achievement) {
            binding.achievement = achievement
            binding.executePendingBindings()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPersonDetailAchievementBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ViewHolder) {
            val team = getItem(position)
            holder.itemView.setOnClickListener {
                onClickListener.onClick(team)
            }
            holder.bind(team)
        } else {
            Log.d("Boooo", "cant bind data")
        }
    }

    companion object CategoryDiffCallback : DiffUtil.ItemCallback<Achievement>() {
        override fun areItemsTheSame(oldItem: Achievement, newItem: Achievement): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Achievement, newItem: Achievement): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class OnClickListener(val clickListener: (achievement: Achievement) -> Unit) {
        fun onClick(achievement: Achievement) = clickListener(achievement)
    }
}