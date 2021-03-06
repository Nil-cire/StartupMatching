package com.eric.startupmatching.profile.following

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eric.startupmatching.data.User
import com.eric.startupmatching.databinding.ItemProfileFollowingRecyclerViewBinding

class ProfileFollowingRecyclerViewAdapter(val viewModel: ProfileFollowingViewModel,
                                          val onClickListener: OnClickListener) : ListAdapter<User, RecyclerView.ViewHolder>(CategoryDiffCallback) {

    class ViewHolder(var binding: ItemProfileFollowingRecyclerViewBinding,
                     var onClickListener: ProfileFollowingRecyclerViewAdapter.OnClickListener,
                     var viewModel: ProfileFollowingViewModel):
        RecyclerView.ViewHolder(binding.root) {

        class OnClickListener(val clickListener: (user: User) -> Unit) {
            fun onClick(user: User) = clickListener(user)
        }


        fun bind(user: User) {
            binding.user = user
            binding.executePendingBindings()
            binding.chatBtn.setOnClickListener { viewModel.setChatUser(user) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProfileFollowingRecyclerViewBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding, onClickListener, viewModel)
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

    companion object CategoryDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class OnClickListener(val clickListener: (user: User) -> Unit) {
        fun onClick(user: User) = clickListener(user)
    }
}