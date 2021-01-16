package com.eric.startupmatching.chat.childfragment

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eric.startupmatching.data.ChatRoom
import com.eric.startupmatching.data.Message
import com.eric.startupmatching.databinding.ItemChatRoomPersonRecyclerViewBinding
import com.google.firebase.firestore.FirebaseFirestore


class ChatRoomTeamAdapter(val onClickListener: OnClickListener) : ListAdapter<ChatRoom, RecyclerView.ViewHolder>(CategoryDiffCallback) {

    class ViewHolder(var binding: ItemChatRoomPersonRecyclerViewBinding):
        RecyclerView.ViewHolder(binding.root) {
        val db = FirebaseFirestore.getInstance()

        fun bind(chatRoom: ChatRoom) {
            binding.chatRoom = chatRoom

            binding.executePendingBindings()
            var messageList = mutableListOf<Message>()
            if (!chatRoom.messages.isNullOrEmpty()) {
                for (messageId in chatRoom.messages) {
                    db.collection("Message")
                        .whereEqualTo("id", messageId)
                        .get()
                        .addOnSuccessListener {
                            it?.let {
                                messageList.add(it.toObjects(Message::class.java)[0])
                                messageList.sortByDescending { it.postTimestamp }
                                binding.lastMessage.text = messageList[0].content
                            }

                        }
                }
            }


//            TODO("Snapshopt message update and change text")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemChatRoomPersonRecyclerViewBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ViewHolder) {
            val chatRoom = getItem(position)
            holder.itemView.setOnClickListener {
                onClickListener.onClick(chatRoom)
            }
            holder.bind(chatRoom)
        } else {
            Log.d("Boooo", "cant bind data")
        }

    }

    companion object CategoryDiffCallback : DiffUtil.ItemCallback<ChatRoom>() {
        override fun areItemsTheSame(oldItem: ChatRoom, newItem: ChatRoom): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ChatRoom, newItem: ChatRoom): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class OnClickListener(val clickListener: (chatRoom: ChatRoom) -> Unit) {
        fun onClick(chatRoom: ChatRoom) = clickListener(chatRoom)
    }
}