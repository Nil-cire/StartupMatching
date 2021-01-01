package com.eric.startupmatching.profile.following

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eric.startupmatching.data.Post
import com.eric.startupmatching.data.User
import com.eric.startupmatching.databinding.ItemSocialmediaPostRecyclerViewBinding
import com.eric.startupmatching.setImage
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFollowingRecyclerViewAdapter(val onClickListener: OnClickListener) : ListAdapter<Post, RecyclerView.ViewHolder>(CategoryDiffCallback) {

    class ViewHolder(var binding: ItemSocialmediaPostRecyclerViewBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post) {
            binding.post = post
            binding.executePendingBindings()

            val db = FirebaseFirestore.getInstance()
            db.collection("User").document(post.poster)
                .get()
                .addOnSuccessListener {
                    var user = it.toObject(User::class.java)
                    binding.userIcon.setImage(user!!.image)
                    binding.userName.text = user.name
                }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSocialmediaPostRecyclerViewBinding.inflate(layoutInflater, parent, false)
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

    companion object CategoryDiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }

    class OnClickListener(val clickListener: (post: Post) -> Unit) {
        fun onClick(post: Post) = clickListener(post)
    }
}