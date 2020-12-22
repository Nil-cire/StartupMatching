package com.eric.startupmatching.project.edit

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eric.startupmatching.data.Team
import com.eric.startupmatching.data.User
import com.eric.startupmatching.databinding.ItemProjectEditTeamBinding
import com.eric.startupmatching.setImage
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.item_project_edit_team.view.*

class ProjectEditTeamAdapter(val onClickListener: OnClickListener, var viewModel: ProjectEditTeamViewModel, val onClickListener2: OnClickListener2) : ListAdapter<Team, RecyclerView.ViewHolder>(CategoryDiffCallback) {

    class ViewHolder(var binding: ItemProjectEditTeamBinding, var viewModel: ProjectEditTeamViewModel):
        RecyclerView.ViewHolder(binding.root) {

//        class OnClickChatListener(val clickListener: (project: Project) -> Unit) {
//            fun onClick(product: Project) = clickListener(product)
//        }

        fun bind(team: Team) {
            binding.team = team
            binding.executePendingBindings()
            binding.addMember.setOnClickListener { viewModel.editTeam.value = team }
            val db = FirebaseFirestore.getInstance()
            db.collection("User")
                .whereEqualTo("id", team.teamLeader)
                .get()
                .addOnSuccessListener {
                    var users = it.toObjects(User::class.java)
                    if (!users.isNullOrEmpty()) {
                        var user = users[0]
                        binding.teamLeaderIcon.setImage(user.image)
                    }

                }
            val adapter = ProjectEditTeamItemAdapter(ProjectEditTeamItemAdapter.OnClickListener{})
            binding.recyclerView.adapter = adapter

            var userList = mutableListOf<User>()
            var memberCount = 0

            if (!team.members.isNullOrEmpty()) {
                for (userId in team.members) {
                    db.collection("User")
                        .whereEqualTo("id", userId)
                        .get()
                        .addOnSuccessListener {
                            userList.add(it.toObjects(User::class.java)[0])
                            memberCount += 1
                            if(memberCount == team.members.size) {
                                adapter.submitList(userList)
                                userList = mutableListOf()
                                memberCount = 0
                            }
                        }
                }
            }
//            binding.addMember.setOnClickListener {
//                SelectMemberDialogAdapter(viewModel).showsDialog
//            }
            db.collection("Team").document(team.id.toString())
                .addSnapshotListener { value, error ->
                    val list = value?.toObject(Team::class.java)?.members
                    if (!list.isNullOrEmpty()) {
                        for (userId in list) {
                            db.collection("User")
                                .whereEqualTo("id", userId)
                                .get()
                                .addOnSuccessListener {
                                    userList.add(it.toObjects(User::class.java)[0])
                                    memberCount += 1
                                    if(memberCount == list.size) {
                                        adapter.submitList(userList)
                                        userList = mutableListOf()
                                        memberCount = 0
                                    }
                                }
                        }
                    }

                }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProjectEditTeamBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

//        val item = getItem(position)
//        holder.bind(item)

        if (holder is ViewHolder) {
            val team = getItem(position)
//            holder.itemView.setOnClickListener {
//                onClickListener.onClick(team)
//            }
            holder.itemView.add_member.setOnClickListener { onClickListener2.onClick(team) }
            holder.bind(team)
        } else {
            Log.d("Boooo", "cant bind data")
        }

    }

    companion object CategoryDiffCallback : DiffUtil.ItemCallback<Team>() {
        override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class OnClickListener(val clickListener: (team: Team) -> Unit) {
        fun onClick(team: Team) = clickListener(team)
    }

    class OnClickListener2(val clickListener: (team: Team) -> Unit) {
        fun onClick(team: Team) = clickListener(team)
    }
}
