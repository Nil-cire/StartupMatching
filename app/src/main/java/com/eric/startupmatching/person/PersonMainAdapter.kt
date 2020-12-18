package com.eric.startupmatching.person

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eric.startupmatching.data.User
import com.eric.startupmatching.databinding.ItemPersonMainRecyclerViewBinding

class PersonMainAdapter(val onClickListener: OnClickListener) : ListAdapter<User, RecyclerView.ViewHolder>(CategoryDiffCallback) {

    class ViewHolder(var binding: ItemPersonMainRecyclerViewBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.user = user
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPersonMainRecyclerViewBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ViewHolder) {
            val user = getItem(position)
            holder.itemView.setOnClickListener {
                onClickListener.onClick(user)
            }
            holder.bind(user)
        } else {
            Log.d("Boooo", "cant bind data")
        }

    }

    companion object CategoryDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class OnClickListener(val clickListener: (team: User) -> Unit) {
        fun onClick(team: User) = clickListener(team)
    }
}