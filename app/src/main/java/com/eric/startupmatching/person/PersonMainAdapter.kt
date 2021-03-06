package com.eric.startupmatching.person

import android.annotation.SuppressLint
import android.os.UserManager
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eric.startupmatching.R
import com.eric.startupmatching.UserInfo
import com.eric.startupmatching.data.FollowStatus
import com.eric.startupmatching.data.User
import com.eric.startupmatching.databinding.ItemPersonMainRecyclerViewBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.time.days

class PersonMainAdapter(val onClickListener: OnClickListener) : ListAdapter<User, RecyclerView.ViewHolder>(CategoryDiffCallback) {

    class ViewHolder(var binding: ItemPersonMainRecyclerViewBinding):
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("ResourceAsColor")
        fun bind(user: User) {
            binding.user = user
            binding.executePendingBindings()
            val adapter = ItemPersonSkillRecyclerViewAdapter(ItemPersonSkillRecyclerViewAdapter.OnClickListener{})
            binding.recyclerView.adapter = adapter
            adapter.submitList(user.skills)
            val db = FirebaseFirestore.getInstance()
            var followed = true
            fun followBtnTexChecker() {
                if (!UserInfo.currentUser.value?.following.isNullOrEmpty()) {
                    if (UserInfo.currentUser.value?.following?.contains(user.id)!!) {
                        binding.iconPin.text = FollowStatus.Followed.type
                        binding.iconPin.setBackgroundResource(R.drawable.round_corner)
                        binding.iconPin.setTextColor(R.color.white)
                        followed = true
                    } else {
                        binding.iconPin.text = FollowStatus.Follow.type
                        binding.iconPin.setBackgroundResource(R.drawable.round_corner_light_blue)
                        binding.iconPin.setTextColor(R.color.black)
                        followed = false
                    }
                } else {
                    binding.iconPin.text = FollowStatus.Follow.type
                    binding.iconPin.setBackgroundResource(R.drawable.round_corner_light_blue)
                    binding.iconPin.setTextColor(R.color.black)
                    followed = false
                }
            }

            fun follow(userId: String) {
                db.collection("User").document(UserInfo.currentUser.value?.id!!)
                    .get()
                    .addOnSuccessListener {doc ->
                        var followingList = mutableListOf<String>()
                        if (!doc.toObject(User::class.java)?.following.isNullOrEmpty()) {
                            followingList = doc.toObject(User::class.java)?.following as MutableList<String>
                        }
                        followingList.add(userId)
                        doc.reference.update("following", followingList)
                        db.collection("User").document(userId)
                            .get()
                            .addOnSuccessListener { doc ->
                                var followerList = mutableListOf<String>()
                                if (!doc.toObject(User::class.java)?.following.isNullOrEmpty()) {
                                    followerList = doc.toObject(User::class.java)?.following as MutableList<String>
                                }
//                                var followerList = doc.toObject(User::class.java)?.follower as MutableList<String>
                                followerList.add(UserInfo.currentUser.value!!.id.toString())
                                doc.reference.update("follower", followerList)
//                                var list = UserInfo.currentUser.value!!.following as MutableList  // up date following list in UserInfo
//                                list.add(userId)
//                                UserInfo.currentUser.value!!.following = list
                                UserInfo.setFollowerList(followerList)
                            }
                            .addOnSuccessListener { followed = true }
                    }
            }

            fun unFollow(userId: String) {
                var followList = mutableListOf<String>()
                db.collection("User").document(UserInfo.currentUser.value?.id!!)
                    .get()
                    .addOnSuccessListener {doc ->
                        if (!doc.toObject(User::class.java)?.following.isNullOrEmpty()) {
                            followList = doc.toObject(User::class.java)?.following as MutableList<String>
                        }
                        var followList2 = mutableListOf<String>()
                        followList.filterTo(followList2, {it != userId})
                        doc.reference.update("following", followList2)
                        db.collection("User").document(userId)
                            .get()
                            .addOnSuccessListener { doc ->
                                var followerList = doc.toObject(User::class.java)?.follower as MutableList<String>
                                var followerList2 = mutableListOf<String>()
                                followerList.filterTo(followerList2, { it != UserInfo.currentUser.value?.id!! })
                                doc.reference.update("follower", followerList2)
                                UserInfo.setFollowerList(followList2)
                            }
                            .addOnSuccessListener { followed = false }
                    }
            }

            followBtnTexChecker()

            binding.iconPin.setOnClickListener {
                if (!followed) {
                    follow(user.id!!)
                    binding.iconPin.text = FollowStatus.Followed.type
                    binding.iconPin.setBackgroundResource(R.drawable.round_corner)
                    binding.iconPin.setTextColor(R.color.white)
                    Log.d("follow", "follow")
                } else {
                    unFollow(user.id!!)
                    binding.iconPin.text = FollowStatus.Follow.type
                    binding.iconPin.setBackgroundResource(R.drawable.round_corner_light_blue)
                    binding.iconPin.setTextColor(R.color.black)
                    Log.d("unFollow", "unFollow")
                }
            }
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