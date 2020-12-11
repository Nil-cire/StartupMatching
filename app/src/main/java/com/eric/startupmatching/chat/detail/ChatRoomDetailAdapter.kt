package com.eric.startupmatching.chat.detail

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eric.startupmatching.UserInfo
import com.eric.startupmatching.data.Message
import com.eric.startupmatching.databinding.ItemChatRoomDetailRecyclerViewBinding
import com.eric.startupmatching.databinding.ItemChatRoomDetailRecyclerViewUserBinding

class ChatRoomDetailAdapter(val onClickListener: OnClickListener) : ListAdapter<Message, RecyclerView.ViewHolder>(CategoryDiffCallback) {

    class ViewHolder(var binding: ItemChatRoomDetailRecyclerViewBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: Message) {
            binding.message = message
            binding.executePendingBindings()
//            binding.sendText.setOnClickListener {
//            }
        }
    }

    class ViewHolderMy(var binding: ItemChatRoomDetailRecyclerViewUserBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: Message) {
            binding.message = message
            binding.executePendingBindings()
//            binding.sendText.setOnClickListener {
//            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).poster) {
            UserInfo.currentUser.value!!.name -> 1
            else -> 0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> ViewHolder(ItemChatRoomDetailRecyclerViewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
            1 -> ViewHolderMy(ItemChatRoomDetailRecyclerViewUserBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

//        val item = getItem(position)
//        holder.bind(item)
        val chatRoom = getItem(position)
        when (holder) {
            is ViewHolder -> {
                holder.itemView.setOnClickListener {
                    onClickListener.onClick(chatRoom)
                }
                holder.bind(chatRoom)
            }
            is ViewHolderMy -> {
                holder.itemView.setOnClickListener {
                    onClickListener.onClick(chatRoom)
                }
                holder.bind(chatRoom)
            }
            else -> {
                Log.d("Boooo", "cant bind data")
            }
        }
    }

    companion object CategoryDiffCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class OnClickListener(val clickListener: (message: Message) -> Unit) {
        fun onClick(message: Message) = clickListener(message)
    }
}