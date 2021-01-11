package com.eric.startupmatching.project.edit.dialog

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eric.startupmatching.R
import com.eric.startupmatching.data.User
import com.eric.startupmatching.databinding.ItemSelectMemberDialogBinding
import com.eric.startupmatching.person.ItemPersonSkillRecyclerViewAdapter

class SelectMemberDialogAdapter(val onClickListener: OnClickListener) : ListAdapter<User, RecyclerView.ViewHolder>(CategoryDiffCallback) {

    class ViewHolder(var binding: ItemSelectMemberDialogBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.user = user
            binding.executePendingBindings()
            val adapter = ItemPersonSkillRecyclerViewAdapter(ItemPersonSkillRecyclerViewAdapter.OnClickListener{})
            binding.recyclerView.adapter = adapter
            adapter.submitList(user.skills)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSelectMemberDialogBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ViewHolder) {
            val user = getItem(position)
            holder.itemView.setOnClickListener {
                onClickListener.onClick(user)
                holder.binding.checkIcon.setBackgroundResource(R.drawable.baseline_task_alt_white_18dp)
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
            return oldItem == newItem
        }
    }

    class OnClickListener(val clickListener: (user: User) -> Unit) {
        fun onClick(user: User) = clickListener(user)
    }
}